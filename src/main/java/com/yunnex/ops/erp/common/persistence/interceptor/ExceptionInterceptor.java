package com.yunnex.ops.erp.common.persistence.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.ServiceException;

import yunnex.common.core.dto.ApiResult;

/**
 * 全局异常捕捉
 * 
 * @author linqunzhi
 * @date 2018年7月30日
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApiResult<Object> result = ApiResult.build();
        if (ex instanceof ServiceException) {
            result.error(ex.getMessage());
        }else {
            log.error(CommonConstants.FailMsg.SYSTEM, ex);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        // 将数据 转换json字符串写出去
        writerJson(result,response);
        return null;
    }

    /**
     * 将数据 转换json字符串写出去
     *
     * @param result
     * @param response
     * @date 2018年7月30日
     * @author linqunzhi
     */
    private void writerJson(ApiResult<Object> result, HttpServletResponse response) {
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(result));
            out.flush();
        } catch (IOException e) {
            log.error("数据写入 response异常", e);
        }
    }

}
