package com.dlu.upms.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Charts {
    private String name;
    private int value1;
    private float value2;
    private List<String> nameList=new ArrayList<>();
    private List<Integer> valueList1=new ArrayList<>();
    private List<Float> valueList2=new ArrayList<>();

}
