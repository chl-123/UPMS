package com.dlu.upms.basicData.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
@Data
@TableName("t_bd_college")
public class CreateCollege{

    private String collegeKey;

    private String collegeName;

}
