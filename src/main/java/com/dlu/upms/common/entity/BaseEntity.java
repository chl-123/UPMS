package com.dlu.upms.common.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @JSONField(format = "yyyy-MM-dd")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}

