package com.dlu.upms.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OwnhoursConstant {
    private final static Map<String, String> verify=new ConcurrentHashMap<>();

    public OwnhoursConstant() {
        verify.put("0","否");
        verify.put("1","是");
    }
    public static String getStatus(String num){
        if (verify.containsKey(num)){
            return verify.get(num);
        }else {
            return "未知";
        }
    }

}
