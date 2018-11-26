package com.yunnex.ops.erp.modules.store.common;

public class StoreUtils {

    private StoreUtils() {}

    public static void checkEditable(int status) {
        if (0 != status && 3 != status) {
            throw new RuntimeException("不可编辑！");
        }
    }
    
}
