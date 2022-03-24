package com.dlu.upms.common.util;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    @Value("${file.baseStoreDir:''}")
    private static String baseStoreDir;
    private static Path fileBasePath;
    public static HttpServletResponse setResponse(HttpServletResponse response,String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        return response;
    }

    public static MultipartFile getMulFileByPath(String picPath) throws IOException {
        File file = new File(picPath);
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        return multipartFile;
    }


    public static void downFile(String openStyle, String name, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //打开方式(inline,attachment)
        openStyle=openStyle==null?"attachment":openStyle;
        String parentPath = getParentPath();
        FileInputStream fis = new FileInputStream(new File(parentPath, name));
        ServletOutputStream os=response.getOutputStream();
        response.setHeader("Content-disposition",openStyle+";filename"+ URLEncoder.encode(name,"UTF-8"));
        IOUtils.copy(fis,os);
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);

    }
    public static void downloadFile(String fileName,HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            ClassPathResource classPathResource = new ClassPathResource("upload/"+fileName);
            File file = classPathResource.getFile();
            InputStream inputStream = classPathResource.getInputStream();
            //输出文件
            InputStream fis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();

            //获取文件的名字再浏览器下载页面
            String name = file.getName();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            out.write(buffer);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件不存在");
        }catch (Exception e) {
            throw e;
        }
    }



    private static String getParentPath() {
        String parentPath = "";
        //默认存储classpath目录，可修改为可配置
        if (StringUtils.isBlank(baseStoreDir)) {
            String rootUri = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            parentPath = rootUri + File.separator + "upload";
        } else {
            parentPath = baseStoreDir;
        }
        return parentPath;
    }
    public static Date parse(String dateInfo) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sf.parse(dateInfo);
        return parse;
    }

    public static boolean isEmpty(String target){
        return target==null||target.equals("");
    }




}
