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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String getKeyByValue(Map map, Integer value) {

        String keys="";

        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();

            Object obj = entry.getValue();

            if (obj != null && obj.equals(value)) {

                keys=(String) entry.getKey();

            }
        }

        return keys;

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

    //修正日期格式。
    public static String parseDate(String dateStr) {
        dateStr=dateStr.replaceAll(" ", "");
        if(dateStr.length()<=10&&!dateStr.contains(":")) {
            if (dateStr.contains("/"))
                dateStr=dateStr.replaceAll("/", "-");
            if (dateStr.contains("."))
                dateStr=dateStr.replaceAll("\\.", "-");
            int start = dateStr.indexOf("-");
            int end = dateStr.lastIndexOf("-");
            String month = null;
            try {
                month = dateStr.substring(start + 1, end);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (month.length() == 1) {
                month = "0" + month;
            }
            String day = dateStr.substring(end + 1);
            if (day.length() == 1) {
                day = "0" + day;
            }
            return dateStr.substring(0, start + 1) + month + "-" + day;
        }else
            return dateStr;
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
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            }
            else {
                return false;
            }
        }
        return false;
    }
    public static Date parse(String dateInfo) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sf.parse(dateInfo);
        return parse;
    }
    public static String formatDate  (Date dateInfo) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(dateInfo);
        return format;
    }
    public static String formatDateTime  (Date dateInfo)  {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(dateInfo);
        return format;
    }

    public static boolean isEmpty(String target){
        return target==null||target.equals("");
    }

    public static Map<String ,Integer> baseFilesMap(String baseFiles){
        Map<String,Integer> fileMap=new HashMap<>();
        if (baseFiles!=null&&!baseFiles.equals("")) {
            String[] approvalSheetFilesList=baseFiles.split(";");
            for (int i = 0; i < approvalSheetFilesList.length; i++) {
                fileMap.put(approvalSheetFilesList[i],1);
            }
        }
        return fileMap;
    }


}
