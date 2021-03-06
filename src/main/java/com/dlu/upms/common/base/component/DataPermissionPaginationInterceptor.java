package com.dlu.upms.common.base.component;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
import com.dlu.upms.common.base.DataPermissionPage;
import com.dlu.upms.common.util.DataPermissionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * 分页拦截器
 *
 * @author hubin
 * @since 2016-01-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataPermissionPaginationInterceptor extends AbstractSqlParserHandler implements Interceptor {

    /**
     * COUNT SQL 解析
     */
    private ISqlParser sqlParser;
    /**
     * 溢出总页数，设置第一页
     */
    private boolean overflow = false;
    /**
     * 方言类型
     */
    private String dialectType;
    /**
     * 方言实现类
     */
    private String dialectClazz;

    @Value("${system.noDataFilterRole}")
    private String noDataFilterRole;

    /**
     * 查询SQL拼接Order By
     *
     * @param originalSql 需要拼接的SQL
     * @param page        page对象
     * @param orderBy     是否需要拼接Order By
     * @return ignore
     */
    public static String concatOrderBy(String originalSql, IPage page, boolean orderBy) {
        if (orderBy && (ArrayUtils.isNotEmpty(page.ascs())
                || ArrayUtils.isNotEmpty(page.descs()))) {
            StringBuilder buildSql = new StringBuilder(originalSql);
            String ascStr = concatOrderBuilder(page.ascs(), " ASC");
            String descStr = concatOrderBuilder(page.descs(), " DESC");
            if (StringUtils.isNotEmpty(ascStr) && StringUtils.isNotEmpty(descStr)) {
                ascStr += ", ";
            }
            if (StringUtils.isNotEmpty(ascStr) || StringUtils.isNotEmpty(descStr)) {
                buildSql.append(" ORDER BY ").append(ascStr).append(descStr);
            }
            return buildSql.toString();
        }
        return originalSql;
    }

    /**
     * 拼接多个排序方法
     *
     * @param columns ignore
     * @param orderWord ignore
     */
    private static String concatOrderBuilder(String[] columns, String orderWord) {
        if (ArrayUtils.isNotEmpty(columns)) {
            return Arrays.stream(columns).filter(StringUtils::isNotEmpty)
                    .map(i -> i + orderWord).collect(joining(StringPool.COMMA));
        }
        return StringUtils.EMPTY;
    }

    /**
     * Physical Page Interceptor for all the queries with parameter {@link RowBounds}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // SQL 解析
        this.sqlParser(metaObject);

        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object paramObj = boundSql.getParameterObject();
        String originalSql = boundSql.getSql();

        // 增加权限的代码判断开始
        // 判断参数里是否有DataPermission对象
        DataPermissionPage dataPermissionPage = null;
        if (paramObj instanceof DataPermissionPage) {
            dataPermissionPage = (DataPermissionPage) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof DataPermissionPage) {
                    dataPermissionPage = (DataPermissionPage) arg;
                    break;
                }
            }
        }
        if (null != dataPermissionPage && !SecurityUtils.getSubject().hasRole(noDataFilterRole)) {
            // 如果没有配置orgId的别名，则默认给一个
            if (StringUtils.isEmpty(dataPermissionPage.getOrgIdAlias())) {
                dataPermissionPage.setOrgIdAlias("organiaztion_id");
            }
            String orgIdAlias = dataPermissionPage.getOrgIdAlias();
            List<String> orgIdList = dataPermissionPage.getOrgIdList();
            String userIdAlias = dataPermissionPage.getUserIdAlias();
            String userId = dataPermissionPage.getUserId();
            boolean ownQuery = dataPermissionPage.isOwnQuery();
            // 如果没有配置userId的别名，则默认给一个
            if (ownQuery && StringUtils.isEmpty(dataPermissionPage.getUserIdAlias())) {
                dataPermissionPage.setUserIdAlias("user_id");
            }
            // 先判断是什么操作，只支持SELECT，DELETE，UPDATE
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            if (SqlCommandType.SELECT.equals(sqlCommandType)) {
                originalSql = DataPermissionUtil.convertDataPermission(originalSql, orgIdAlias, orgIdList, ownQuery, userIdAlias, userId);
            }
        }
        // 增加权限的代码判断结束

        // 判断参数里是否有page对象
        IPage page = null;
        if (paramObj instanceof IPage) {
            page = (IPage) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof IPage) {
                    page = (IPage) arg;
                    break;
                }
            }
        }

        /*
         * 不需要分页的场合，如果 size 小于 0 返回结果集
         */
        if (null == page || page.getSize() < 0) {
            return invocation.proceed();
        }

        Connection connection = (Connection) invocation.getArgs()[0];
        DbType dbType = StringUtils.isNotEmpty(dialectType) ? DbType.getDbType(dialectType)
                : JdbcUtils.getDbType(connection.getMetaData().getURL());

        boolean orderBy = true;
        if (page.isSearchCount()) {
            SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(page.optimizeCountSql(), sqlParser, originalSql);
            orderBy = sqlInfo.isOrderBy();
            this.queryTotal(overflow, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
//            if (page.getTotal() <= 0) {
//                return invocation.proceed();
//            }
        }

        String buildSql = concatOrderBy(originalSql, page, orderBy);
        DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
        Configuration configuration = mappedStatement.getConfiguration();
        List<ParameterMapping> mappings = new ArrayList<>(boundSql.getParameterMappings());
        Map<String, Object> additionalParameters = (Map<String, Object>) metaObject.getValue("delegate.boundSql.additionalParameters");
        model.consumers(mappings, configuration, additionalParameters);
        metaObject.setValue("delegate.boundSql.sql", model.getDialectSql());
        metaObject.setValue("delegate.boundSql.parameterMappings", mappings);
        return invocation.proceed();
    }

    /**
     * 查询总记录条数
     *
     * @param sql             count sql
     * @param mappedStatement MappedStatement
     * @param boundSql        BoundSql
     * @param page            IPage
     * @param connection      Connection
     */
    protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql, IPage page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            long total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
             */
            long pages = page.getPages();
            if (overflowCurrent && page.getCurrent() > pages) {
                // 设置为第一条
                page.setCurrent(1);
            }
        } catch (Exception e) {
            throw ExceptionUtils.mpe("Error: Method queryTotal execution error of sql : \n %s \n", e, sql);
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }

    public DataPermissionPaginationInterceptor setSqlParser(ISqlParser sqlParser) {
        this.sqlParser = sqlParser;
        return this;
    }

    public DataPermissionPaginationInterceptor setOverflow(boolean overflow) {
        this.overflow = overflow;
        return this;
    }

    public DataPermissionPaginationInterceptor setDialectType(String dialectType) {
        this.dialectType = dialectType;
        return this;
    }

    public DataPermissionPaginationInterceptor setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
        return this;
    }
}
