package com.dlu.upms.common.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dlu.upms.common.base.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 工具类
 * @Date 2018-06-06
 * @Time 14:07
 */
public class ExcelUtil {
    public static <T extends BaseRowModel> List<T> read(MultipartFile file, Class<T> rowModel) throws Exception{

        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getExcelReader(file,excelListener,true);
        if(excelReader == null){
            return new ArrayList();
        }
        for(Sheet sheet:excelReader.getSheets()){
            sheet.setClazz(rowModel);
            excelReader.read(sheet);
        }
        List<T> list = new ArrayList<>();
        for(Object obj:excelListener.getDatas()){
            list.add((T)obj);
        }
        return list;
    }
    public static <T extends BaseRowModel> List<T> read(MultipartFile file, Class<T> rowModel, String sheetName
    ) throws Exception{

        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getExcelReader(file,excelListener,true);
        if(excelReader == null){
            return new ArrayList();
        }
        Map<String,Integer> map=new HashMap<>();
        for(Sheet sheet:excelReader.getSheets()){
            if (sheet.getSheetName().equals(sheetName)) {
                map.put(sheetName,1);
                sheet.setClazz(rowModel);
                excelReader.read(sheet);
            }
        }
        if (map == null||map.size()==0) {
            throw new BusinessException("Excel表格中没有【"+sheetName+"】这个Excel表");
        }
        List<T> list = new ArrayList<>();
        for(Object obj:excelListener.getDatas()){
            list.add((T)obj);
        }
        return list;
    }


    /**
     *
     * @param file 文件输入流

     * @param eventListener 用户监听
     * @param trim 是否对解析的String做trim()默认true,用于防止excel中空格引起的装换报错
     * @return
     */
    public static ExcelReader getExcelReader(MultipartFile file,

                                             AnalysisEventListener eventListener, boolean trim) throws Exception{
//        String fileName  = file.getName();
        String fileName=file.getOriginalFilename();
        if (fileName == null ) {
            throw new Exception("文件格式错误！");
        }
        if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
            throw new Exception("文件格式错误！");
        }
        InputStream inputStream = null;
        try{
//            inputStream = new FileInputStream(file);
            inputStream=file.getInputStream();
            if (fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue())) {
                return new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, eventListener, false);
            } else {
                return new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, eventListener, false);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



}
