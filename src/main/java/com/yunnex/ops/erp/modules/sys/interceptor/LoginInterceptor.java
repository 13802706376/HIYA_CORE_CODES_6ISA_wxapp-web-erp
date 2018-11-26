package com.yunnex.ops.erp.modules.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunnex.ops.erp.common.constants.LoginConstants;
import com.yunnex.ops.erp.common.result.BaseResult;
import com.yunnex.ops.erp.common.utils.JedisUtils;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private ErpShopInfoService erpShopInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	//测试部署情况，屏蔽
	    if(-1 != handler.toString().indexOf("TestApiController.deployment"))
	    {
	    	 return true;
	    }
	    String shopToken = request.getHeader(LoginConstants.SHOP_TOKEN);
        LOGGER.info("SHOP_TOKEN : {}", shopToken);
        
        if (StringUtils.isNotEmpty(shopToken)) {    // 登录过
            String[] tokens = shopToken.split("-");
            long start = Long.parseLong(tokens[1]);
            long now = System.currentTimeMillis();
            long duration = now - start;
            // session超时之前的时间(提前10秒)：sesion时间 - 10秒
            long offset = LoginConstants.SESSION_TIMEOUT * 1000L - 10000L;
            // session超时之前，不用更新在redis中的时间
            if (duration < offset) {
                return true;
            }
            
            // 快要超时时,更新有效期
            response.setHeader(LoginConstants.SHOP_TOKEN, tokens[0] + "-" + now);
            String sessionKey = LoginConstants.SESSION_PREFIX + tokens[0];
            String erpShopInfoJson = JedisUtils.get(sessionKey);
            // 如果redis中有登录信息,放行
            if (StringUtils.isNotEmpty(erpShopInfoJson)) {
                JedisUtils.set(sessionKey, erpShopInfoJson, LoginConstants.SESSION_TIMEOUT);    // 更新session有效期
                return true;
            }
        }
        
        // 如果刚刚建立会话，查看有没有记住登录信息
        String rememberMe = request.getHeader(LoginConstants.REMEMBER_ME);
        LOGGER.info("REMEMBER_ME : {}", rememberMe);
        if (StringUtils.isNotEmpty(rememberMe)) {
            // 自动登录
            String decode = new String(Base64Utils.decodeFromString(rememberMe));
            String[] split = decode.split(":");
            if (split.length >= 3) {
                BaseResult baseResult = erpShopInfoService.login(split[0], split[1], split[2], true, response);
                if (BaseResult.isSuccess(baseResult)) {
                    return true;
                } else {
                    response.setHeader(LoginConstants.REMEMBER_ME, "");
                }
            }
        }
        String url = request.getRequestURI();
        LOGGER.info("url =  {}, SESSIONINVALID = true , 未登录", url);
        response.setHeader("SESSIONINVALID", "true"); // 会话已失效标识（未登录）
        return false;
    }

}
