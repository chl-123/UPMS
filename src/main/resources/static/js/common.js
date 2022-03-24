(function ($) {
    $.extend({

        // 表单封装处理
        form: {
            // 表单重置
            reset: function(formId, tableId, pageNumber, pageSize) {
                table.set(tableId);
                formId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $("#" + formId)[0].reset();
                var tableId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                if (table.options.type == table_type.bootstrapTable) {
                    var params = $("#" + tableId).bootstrapTable('getOptions');
                    if ($.common.isNotEmpty(pageNumber)) {
                        params.pageNumber = pageNumber;
                    }
                    if ($.common.isNotEmpty(pageSize)) {
                        params.pageSize = pageSize;
                    }
                    $("#" + tableId).bootstrapTable('refresh', params);
                } else if (table.options.type == table_type.bootstrapTreeTable) {
                    $("#" + tableId).bootstrapTreeTable('refresh', []);
                }
                if ($.common.isNotEmpty(startLayDate) && $.common.isNotEmpty(endLayDate)) {
                    endLayDate.config.min.year = '';
                    endLayDate.config.min.month = '';
                    endLayDate.config.min.date = '';
                    startLayDate.config.max.year = '2099';
                    startLayDate.config.max.month = '12';
                    startLayDate.config.max.date = '31';
                }
            },
            // 获取选中复选框项
            selectCheckeds: function(name) {
                var checkeds = "";
                $('input:checkbox[name="' + name + '"]:checked').each(function(i) {
                    if (0 == i) {
                        checkeds = $(this).val();
                    } else {
                        checkeds += ("," + $(this).val());
                    }
                });
                return checkeds;
            },
            // 获取选中下拉框项
            selectSelects: function(name) {
                var selects = "";
                $('#' + name + ' option:selected').each(function (i) {
                    if (0 == i) {
                        selects = $(this).val();
                    } else {
                        selects += ("," + $(this).val());
                    }
                });
                return selects;
            }
        },
        // 弹出层封装处理
        modal: {
            // 显示图标
            icon: function(type) {
                var icon = "";
                if (type == modal_status.WARNING) {
                    icon = 0;
                } else if (type == modal_status.SUCCESS) {
                    icon = 1;
                } else if (type == modal_status.FAIL) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            // 消息提示
            msg: function(content, type) {
                if (type != undefined) {
                    top.layer.msg(content, { icon: $.modal.icon(type), time: 1000, shift: 5 });
                } else {
                    top.layer.msg(content);
                }
            },
            // 错误消息
            msgError: function(content) {
                $.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function(content) {
                $.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function(content) {
                $.modal.msg(content, modal_status.WARNING);
            },
            // 弹出提示
            alert: function(content, type) {
                top.layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系统提示",
                    btn: ['确认'],
                    btnclass: ['btn btn-primary'],
                });
            },
            // 错误提示
            alertError: function(content) {
                $.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function(content) {
                $.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function(content) {
                $.modal.alert(content, modal_status.WARNING);
            },
            // 消息提示，重新加载页面
            msgReload: function(msg, type) {
                top.layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function() {
                        $.modal.reload();
                    });
            },
            // 消息提示成功并刷新父窗体
            msgSuccessReload: function(msg) {
                $.modal.msgReload(msg, modal_status.SUCCESS);
            },
            // 获取iframe页的DOM
            getChildFrame: function (index) {
                if($.common.isEmpty(index)){
                    var index = parent.layer.getFrameIndex(window.name);
                    return parent.layer.getChildFrame('body', index);
                } else {
                    return top.layer.getChildFrame('body', index);
                }
            },
            // 关闭窗体
            close: function (index) {
                if($.common.isEmpty(index)){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                } else {
                    top.layer.close(index);
                }
            },
            // 关闭全部窗体
            closeAll: function () {
                top.layer.closeAll();
            },
            // 确认窗体
            confirm: function (content, callBack) {
                top.layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ['确认', '取消']
                }, function (index) {
                    $.modal.close(index);
                    callBack(true);
                });
            },
            // 弹出层指定宽度
            open: function (title, url, width, height, callback) {
                // 如果是移动端，就使用自适应大小弹窗
                if ($.common.isMobile()) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                if ($.common.isEmpty(callback)) {
                    callback = function(index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    }
                }
                top.layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['确定', '关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: callback,
                    cancel: function(index) {
                        return true;
                    }
                });
            },
            // 弹出层指定参数选项
            openOptions: function (options) {
                var _url = $.common.isEmpty(options.url) ? "/404.html" : options.url;
                var _title = $.common.isEmpty(options.title) ? "系统窗口" : options.title;
                var _width = $.common.isEmpty(options.width) ? "800" : options.width;
                var _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                var _btn = ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'];
                // 如果是移动端，就使用自适应大小弹窗
                if ($.common.isMobile()) {
                    _width = 'auto';
                    _height = 'auto';
                }
                if ($.common.isEmpty(options.yes)) {
                    options.yes = function(index, layero) {
                        options.callBack(index, layero);
                    }
                }
                var btnCallback = {};
                if(options.btn instanceof Array){
                    for (var i = 1, len = options.btn.length; i < len; i++) {
                        var btn = options["btn" + (i + 1)];
                        if (btn) {
                            btnCallback["btn" + (i + 1)] = btn;
                        }
                    }
                }
                var index = top.layer.open($.extend({
                    id: options.id,       // 唯一id
                    anim: options.anim,   // 弹出动画 0-6
                    type: 2,
                    maxmin: $.common.isEmpty(options.maxmin) ? true : options.maxmin,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    shadeClose: $.common.isEmpty(options.shadeClose) ? true : options.shadeClose,
                    skin: options.skin,
                    // options.btn设置为0表示不显示按钮
                    btn: $.common.isEmpty(options.btn) ? _btn : options.btn,
                    yes: options.yes,
                    cancel: function () {
                        return true;
                    }
                }, btnCallback));
                if ($.common.isNotEmpty(options.full) && options.full === true) {
                    top.layer.full(index);
                }
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
                // 如果是移动端，就使用自适应大小弹窗
                if ($.common.isMobile()) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                var index = top.layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['确定', '关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: function(index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    },
                    cancel: function(index) {
                        return true;
                    }
                });
                top.layer.full(index);
            },
            // 选卡页方式打开
            openTab: function (title, url, isRefresh) {
                createMenuItem(url, title, isRefresh);
            },
            // 选卡页同一页签打开
            parentTab: function (title, url) {
                var dataId = window.frameElement.getAttribute('data-id');
                createMenuItem(url, title);
                closeItem(dataId);
            },
            // 关闭选项卡
            closeTab: function (dataId) {
                closeItem(dataId);
            },
            // 禁用按钮
            disable: function() {
                var doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).addClass("layer-disabled");
            },
            // 启用按钮
            enable: function() {
                var doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).removeClass("layer-disabled");
            },
            // 打开遮罩层
            loading: function (message) {
                $.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            // 关闭遮罩层
            closeLoading: function () {
                setTimeout(function(){
                    $.unblockUI();
                }, 50);
            },
            // 重新加载
            reload: function () {
                parent.location.reload();
            }
        },
        // 操作封装处理
        operate: {
            // 提交数据
            submit: function(url, type, dataType, data, callback) {
                var config = {
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.ajaxSuccess(result);
                    }
                };
                $.ajax(config)
            },
            // post请求传输
            post: function(url, data, callback) {
                $.operate.submit(url, "post", "json", data, callback);
            },
            // get请求传输
            get: function(url, callback) {
                $.operate.submit(url, "get", "json", "", callback);
            },
            // 详细信息
            detail: function(id, width, height) {
                table.set();
                var _url = $.operate.detailUrl(id);
                var options = {
                    title: table.options.modalName + "详细",
                    width: width,
                    height: height,
                    url: _url,
                    skin: 'layui-layer-gray',
                    btn: ['关闭'],
                    yes: function (index, layero) {
                        $.modal.close(index);
                    }
                };
                $.modal.openOptions(options);
            },
            // 详细信息，以tab页展现
            detailTab: function(id) {
                table.set();
                $.modal.openTab("详细" + table.options.modalName, $.operate.detailUrl(id));
            },
            // 详细访问地址
            detailUrl: function(id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.detailUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = table.options.detailUrl.replace("{id}", id);
                }
                return url;
            },
            // 删除信息
            remove: function(id) {
                table.set();
                $.modal.confirm("确定删除该条" + table.options.modalName + "信息吗？", function() {
                    var url = $.common.isEmpty(id) ? table.options.removeUrl : table.options.removeUrl.replace("{id}", id);
                    if(table.options.type == table_type.bootstrapTreeTable) {
                        $.operate.get(url);
                    } else {
                        var data = { "ids": id };
                        $.operate.submit(url, "post", "json", data);
                    }
                });
            },
            // 批量删除信息
            removeAll: function() {
                table.set();
                var rows = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                if (rows.length == 0) {
                    $.modal.alertWarning("请至少选择一条记录");
                    return;
                }
                $.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
                    var url = table.options.removeUrl;
                    var data = { "ids": rows.join() };
                    $.operate.submit(url, "post", "json", data);
                });
            },
            // 清空信息
            clean: function() {
                table.set();
                $.modal.confirm("确定清空所有" + table.options.modalName + "吗？", function() {
                    var url = table.options.cleanUrl;
                    $.operate.submit(url, "post", "json", "");
                });
            },
            // 添加信息
            add: function(id) {
                table.set();
                $.modal.open("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加信息，以tab页展现
            addTab: function (id) {
                table.set();
                $.modal.openTab("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加信息 全屏
            addFull: function(id) {
                table.set();
                $.modal.openFull("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加访问地址
            addUrl: function(id) {
                var url = $.common.isEmpty(id) ? table.options.createUrl.replace("{id}", "") : table.options.createUrl.replace("{id}", id);
                return url;
            },
            // 修改信息
            edit: function(id) {
                table.set();
                if($.common.isEmpty(id) && table.options.type == table_type.bootstrapTreeTable) {
                    var row = $("#" + table.options.id).bootstrapTreeTable('getSelections')[0];
                    if ($.common.isEmpty(row)) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    var url = table.options.updateUrl.replace("{id}", row[table.options.uniqueId]);
                    $.modal.open("修改" + table.options.modalName, url);
                } else {
                    $.modal.open("修改" + table.options.modalName, $.operate.editUrl(id));
                }
            },
            // 修改信息，以tab页展现
            editTab: function(id) {
                table.set();
                $.modal.openTab("修改" + table.options.modalName, $.operate.editUrl(id));
            },
            // 修改信息 全屏
            editFull: function(id) {
                table.set();
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.updateUrl.replace("{id}", id);
                } else {
                    if(table.options.type == table_type.bootstrapTreeTable) {
                        var row = $("#" + table.options.id).bootstrapTreeTable('getSelections')[0];
                        if ($.common.isEmpty(row)) {
                            $.modal.alertWarning("请至少选择一条记录");
                            return;
                        }
                        url = table.options.updateUrl.replace("{id}", row[table.options.uniqueId]);
                    } else {
                        var row = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                        url = table.options.updateUrl.replace("{id}", row);
                    }
                }
                $.modal.openFull("修改" + table.options.modalName, url);
            },
            // 修改访问地址
            editUrl: function(id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.updateUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = table.options.updateUrl.replace("{id}", id);
                }
                return url;
            },
            // 保存信息 刷新表格
            save: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍候...");
                        $.modal.disable();
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存信息 弹出结果提示框
            saveModal: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        if (result.code == web_status.SUCCESS) {
                            $.modal.alertSuccess(result.msg)
                        } else if (result.code == web_status.WARNING) {
                            $.modal.alertWarning(result.msg)
                        } else {
                            $.modal.alertError(result.msg);
                        }
                        $.modal.closeLoading();
                    }
                };
                $.ajax(config)
            },
            // 保存选项卡信息
            saveTab: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successTabCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
                if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTable) {
                    $.modal.msgSuccess(result.msg);
                    $.table.refresh();
                } else if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTreeTable) {
                    $.modal.msgSuccess(result.msg);
                    $.treeTable.refresh();
                } else if (result.code == web_status.SUCCESS && $.common.isEmpty(table.options.type)) {
                    $.modal.msgSuccess(result.msg)
                }  else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 保存结果重新加载页面
            saveReload: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgSuccessReload(result.msg);
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 成功回调执行事件（父窗体静默更新）
            successCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                    var parent = activeWindow();
                    if($.common.isEmpty(parent.table)) {
                        $.modal.msgSuccessReload(result.msg);
                    } else if (parent.table.options.type == table_type.bootstrapTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.table.refresh();
                    } else if (parent.table.options.type == table_type.bootstrapTreeTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.treeTable.refresh();
                    }
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
                $.modal.enable();
            },
            // 选项卡成功回调执行事件（父窗体静默更新）
            successTabCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                    var topWindow = $(window.parent.document);
                    var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-panel');
                    var $contentWindow = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow)[0].contentWindow;
                    $.modal.close();
                    $contentWindow.$.modal.msgSuccess(result.msg);
                    $contentWindow.$(".layui-layer-padding").removeAttr("style");
                    if ($contentWindow.table.options.type == table_type.bootstrapTable) {
                        $contentWindow.$.table.refresh();
                    } else if ($contentWindow.table.options.type == table_type.bootstrapTreeTable) {
                        $contentWindow.$.treeTable.refresh();
                    }
                    $.modal.closeTab();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 校验封装处理
        validate: {
            // 判断返回标识是否唯一 false 不存在 true 存在
            unique: function (value) {
                if (value == "0") {
                    return true;
                }
                return false;
            },
            // 表单验证
            form: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().form();
            },
            // 重置表单验证（清除提示信息）
            reset: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().resetForm();
            }
        },
        // 树插件封装处理
        tree: {
            _option: {},
            _lastValue: {},
            // 初始化树结构
            init: function(options) {
                var defaults = {
                    id: "tree",                    // 属性ID
                    expandLevel: 0,                // 展开等级节点
                    view: {
                        selectedMulti: false,      // 设置是否允许同时选中多个节点
                        nameIsHTML: true           // 设置 name 属性是否支持 HTML 脚本
                    },
                    check: {
                        enable: false,             // 置 zTree 的节点上是否显示 checkbox / radio
                        nocheckInherit: true,      // 设置子节点是否自动继承
                        chkboxType: { "Y": "ps", "N": "ps" } // 父子节点的关联关系
                    },
                    data: {
                        key: {
                            title: "title"         // 节点数据保存节点提示信息的属性名称
                        },
                        simpleData: {
                            enable: true           // true / false 分别表示 使用 / 不使用 简单数据模式
                        }
                    },
                };
                var options = $.extend(defaults, options);
                $.tree._option = options;
                // 树结构初始化加载
                var setting = {
                    callback: {
                        onClick: options.onClick,                      // 用于捕获节点被点击的事件回调函数
                        onCheck: options.onCheck,                      // 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                        onDblClick: options.onDblClick                 // 用于捕获鼠标双击之后的事件回调函数
                    },
                    check: options.check,
                    view: options.view,
                    data: options.data
                };
                $.get(options.url, function(data) {
                    var treeId = $("#treeId").val();
                    tree = $.fn.zTree.init($("#" + options.id), setting, data);
                    $._tree = tree;
                    for (var i = 0; i < options.expandLevel; i++) {
                        var nodes = tree.getNodesByParam("level", i);
                        for (var j = 0; j < nodes.length; j++) {
                            tree.expandNode(nodes[j], true, false, false);
                        }
                    }
                    var node = tree.getNodesByParam("id", treeId, null)[0];
                    $.tree.selectByIdName(treeId, node);
                    // 回调tree方法
                    if(typeof(options.callBack) === "function"){
                        options.callBack(tree);
                    }
                });
            },
            // 搜索节点
            searchNode: function() {
                // 取得输入的关键字的值
                var value = $.common.trim($("#keyword").val());
                if ($.tree._lastValue == value) {
                    return;
                }
                // 保存最后一次搜索名称
                $.tree._lastValue = value;
                var nodes = $._tree.getNodes();
                // 如果要查空字串，就退出不查了。
                if (value == "") {
                    $.tree.showAllNode(nodes);
                    return;
                }
                $.tree.hideAllNode(nodes);
                // 根据搜索值模糊匹配
                $.tree.updateNodes($._tree.getNodesByParamFuzzy("name", value));
            },
            // 根据Id和Name选中指定节点
            selectByIdName: function(treeId, node) {
                if ($.common.isNotEmpty(treeId) && node && treeId == node.id) {
                    $._tree.selectNode(node, true);
                }
            },
            // 显示所有节点
            showAllNode: function(nodes) {
                nodes = $._tree.transformToArray(nodes);
                for (var i = nodes.length - 1; i >= 0; i--) {
                    if (nodes[i].getParentNode() != null) {
                        $._tree.expandNode(nodes[i], true, false, false, false);
                    } else {
                        $._tree.expandNode(nodes[i], true, true, false, false);
                    }
                    $._tree.showNode(nodes[i]);
                    $.tree.showAllNode(nodes[i].children);
                }
            },
            // 隐藏所有节点
            hideAllNode: function(nodes) {
                var nodes = $._tree.transformToArray(nodes);
                for (var i = nodes.length - 1; i >= 0; i--) {
                    $._tree.hideNode(nodes[i]);
                }
            },
            // 显示所有父节点
            showParent: function(treeNode) {
                var parentNode;
                while ((parentNode = treeNode.getParentNode()) != null) {
                    $._tree.showNode(parentNode);
                    $._tree.expandNode(parentNode, true, false, false);
                    treeNode = parentNode;
                }
            },
            // 显示所有孩子节点
            showChildren: function(treeNode) {
                if (treeNode.isParent) {
                    for (var idx in treeNode.children) {
                        var node = treeNode.children[idx];
                        $._tree.showNode(node);
                        $.tree.showChildren(node);
                    }
                }
            },
            // 更新节点状态
            updateNodes: function(nodeList) {
                $._tree.showNodes(nodeList);
                for (var i = 0, l = nodeList.length; i < l; i++) {
                    var treeNode = nodeList[i];
                    $.tree.showChildren(treeNode);
                    $.tree.showParent(treeNode)
                }
            },
            // 获取当前被勾选集合
            getCheckedNodes: function(column) {
                var _column = $.common.isEmpty(column) ? "id" : column;
                var nodes = $._tree.getCheckedNodes(true);
                return $.map(nodes, function (row) {
                    return row[_column];
                }).join();
            },
            // 不允许根父节点选择
            notAllowParents: function(_tree) {
                var nodes = _tree.getSelectedNodes();
                if(nodes.length == 0){
                    $.modal.msgError("请选择节点后提交");
                    return false;
                }
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].level == 0) {
                        $.modal.msgError("不能选择根节点（" + nodes[i].name + "）");
                        return false;
                    }
                    if (nodes[i].isParent) {
                        $.modal.msgError("不能选择父节点（" + nodes[i].name + "）");
                        return false;
                    }
                }
                return true;
            },
            // 不允许最后层级节点选择
            notAllowLastLevel: function(_tree) {
                var nodes = _tree.getSelectedNodes();
                for (var i = 0; i < nodes.length; i++) {
                    if (!nodes[i].isParent) {
                        $.modal.msgError("不能选择最后层级节点（" + nodes[i].name + "）");
                        return false;
                    }
                }
                return true;
            },
            // 隐藏/显示搜索栏
            toggleSearch: function() {
                $('#search').slideToggle(200);
                $('#btnShow').toggle();
                $('#btnHide').toggle();
                $('#keyword').focus();
            },
            // 折叠
            collapse: function() {
                $._tree.expandAll(false);
            },
            // 展开
            expand: function() {
                $._tree.expandAll(true);
            }
        },
        // 通用方法封装处理
        common: {
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            // 空对象转字符串
            nullToStr: function(value) {
                if ($.common.isEmpty(value)) {
                    return "-";
                }
                return value;
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 比较两个字符串（大小写敏感）
            equals: function (str, that) {
                return str == that;
            },
            // 比较两个字符串（大小写不敏感）
            equalsIgnoreCase: function (str, that) {
                return String(str).toUpperCase() === String(that).toUpperCase();
            },
            // 将字符串按指定字符分割
            split: function (str, sep, maxLen) {
                if ($.common.isEmpty(str)) {
                    return null;
                }
                var value = String(str).split(sep);
                return maxLen ? value.slice(0, maxLen - 1) : value;
            },
            // 字符串格式化(%s )
            sprintf: function (str) {
                var args = arguments, flag = true, i = 1;
                str = str.replace(/%s/g, function () {
                    var arg = args[i++];
                    if (typeof arg === 'undefined') {
                        flag = false;
                        return '';
                    }
                    return arg == null ? '' : arg;
                });
                return flag ? str : '';
            },
            // 日期格式化 时间戳  -> yyyy-MM-dd HH-mm-ss
            dateFormat: function(date, format) {
                var that = this;
                if (that.isEmpty(date)) return "";
                if (!date) return;
                if (!format) format = "yyyy-MM-dd";
                switch (typeof date) {
                    case "string":
                        date = new Date(date.replace(/-/g, "/"));
                        break;
                    case "number":
                        date = new Date(date);
                        break;
                }
                if (!date instanceof Date) return;
                var dict = {
                    "yyyy": date.getFullYear(),
                    "M": date.getMonth() + 1,
                    "d": date.getDate(),
                    "H": date.getHours(),
                    "m": date.getMinutes(),
                    "s": date.getSeconds(),
                    "MM": ("" + (date.getMonth() + 101)).substr(1),
                    "dd": ("" + (date.getDate() + 100)).substr(1),
                    "HH": ("" + (date.getHours() + 100)).substr(1),
                    "mm": ("" + (date.getMinutes() + 100)).substr(1),
                    "ss": ("" + (date.getSeconds() + 100)).substr(1)
                };
                return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g,
                    function() {
                        return dict[arguments[0]];
                    });
            },
            // 获取节点数据，支持多层级访问
            getItemField: function (item, field) {
                var value = item;
                if (typeof field !== 'string' || item.hasOwnProperty(field)) {
                    return item[field];
                }
                var props = field.split('.');
                for (var p in props) {
                    value = value && value[props[p]];
                }
                return value;
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 判断字符串是否是以start开头
            startWith: function(value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判断字符串是否是以end结尾
            endWith: function(value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
            },
            // 数组去重
            uniqueFn: function(array) {
                var result = [];
                var hashObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (!hashObj[array[i]]) {
                        hashObj[array[i]] = true;
                        result.push(array[i]);
                    }
                }
                return result;
            },
            // 数组中的所有元素放入一个字符串
            join: function(array, separator) {
                if ($.common.isEmpty(array)) {
                    return null;
                }
                return array.join(separator);
            },
            // 获取form下所有的字段并转换为json对象
            formToJSON: function(formId) {
                var json = {};
                $.each($("#" + formId).serializeArray(), function(i, field) {
                    if(json[field.name]) {
                        json[field.name] += ("," + field.value);
                    } else {
                        json[field.name] = field.value;
                    }
                });
                return json;
            },
            // 数据字典转下拉框
            dictToSelect: function(datas, value, name) {
                var actions = [];
                actions.push($.common.sprintf("<select class='form-control' name='%s'>", name));
                $.each(datas, function(index, dict) {
                    actions.push($.common.sprintf("<option value='%s'", dict.dictValue));
                    if (dict.dictValue == ('' + value)) {
                        actions.push(' selected');
                    }
                    actions.push($.common.sprintf(">%s</option>", dict.dictLabel));
                });
                actions.push('</select>');
                return actions.join('');
            },
            // 获取obj对象长度
            getLength: function(obj) {
                var count = 0;
                for (var i in obj) {
                    if (obj.hasOwnProperty(i)) {
                        count++;
                    }
                }
                return count;
            },
            // 判断移动端
            isMobile: function () {
                return navigator.userAgent.match(/(Android|iPhone|SymbianOS|Windows Phone|iPad|iPod)/i);
            },
            // 数字正则表达式，只能为0-9数字
            numValid : function(text){
                var patten = new RegExp(/^[0-9]+$/);
                return patten.test(text);
            },
            // 英文正则表达式，只能为a-z和A-Z字母
            enValid : function(text){
                var patten = new RegExp(/^[a-zA-Z]+$/);
                return patten.test(text);
            },
            // 英文、数字正则表达式，必须包含（字母，数字）
            enNumValid : function(text){
                var patten = new RegExp(/^(?=.*[a-zA-Z]+)(?=.*[0-9]+)[a-zA-Z0-9]+$/);
                return patten.test(text);
            },
            // 英文、数字、特殊字符正则表达式，必须包含（字母，数字，特殊字符!@#$%^&*()-=_+）
            charValid : function(text){
                var patten = new RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#\$%\^&\*\(\)\-=_\+])[A-Za-z\d~!@#\$%\^&\*\(\)\-=_\+]{6,}$/);
                return patten.test(text);
            },
        }
    });
})(jQuery);