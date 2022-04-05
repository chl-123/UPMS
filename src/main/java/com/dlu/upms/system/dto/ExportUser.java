package com.dlu.upms.system.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExportUser implements Serializable {

    @ExcelProperty(value="账号",index = 0)
    private String userAccount;

    @ExcelProperty(value="姓名",index = 1)
    private String userName;

    @ExcelProperty(value="邮箱",index = 2)
    private String userEmail;

    @ExcelProperty(value="性别",index = 3)
    private String userSex;

    @ExcelProperty(value="联系方式",index = 4)
    private String userPhone;

    @ExcelProperty(value="是否校内",index = 5)
    private String isInside;

    @ExcelProperty(value="学院",index = 6)
    private String collegeName;
}

