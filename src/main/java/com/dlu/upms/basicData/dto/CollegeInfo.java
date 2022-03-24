package com.dlu.upms.basicData.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeInfo {

    private String id;

    private String collegeKey;

    private String collegeName;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String delFlag;


}
