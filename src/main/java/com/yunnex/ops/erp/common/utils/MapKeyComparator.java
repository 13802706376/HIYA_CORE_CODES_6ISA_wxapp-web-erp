package com.yunnex.ops.erp.common.utils;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        String taskConsumTime1 = str1.substring(0,str1.indexOf("_"));
        String taskConsumTime2  =str1.substring(0,str2.indexOf("_"));
        
        return taskConsumTime2.compareTo(taskConsumTime1);
    }
}
