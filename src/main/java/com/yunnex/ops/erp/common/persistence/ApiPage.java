package com.yunnex.ops.erp.common.persistence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiPage<T> extends Page<T> {
    
    public ApiPage(HttpServletRequest request, HttpServletResponse response){
        super(request, response);
    }

    @Override
    public String toString() {
        return "";
    }
    
}
