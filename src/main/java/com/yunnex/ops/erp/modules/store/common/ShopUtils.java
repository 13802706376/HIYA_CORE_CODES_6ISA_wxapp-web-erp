package com.yunnex.ops.erp.modules.store.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunnex.ops.erp.common.constants.LoginConstants;
import com.yunnex.ops.erp.common.utils.JedisUtils;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;

@Component
public class ShopUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopUtils.class);

    @Autowired
    private HttpServletRequest request;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 获取登录商户
     */
    public ErpShopInfo getLoginShop() {
        String sessionKey = getSessionKey();
        LOGGER.info("getLoginShop | sessionKey = {}", sessionKey);
        if (StringUtils.isNotEmpty(sessionKey)) {
            String erpShopInfoJson = JedisUtils.get(sessionKey);
            LOGGER.info("getLoginShop | erpShopInfoJson = {}", erpShopInfoJson);
            ErpShopInfo erpShopInfo = JSON.parseObject(erpShopInfoJson, ErpShopInfo.class);
            if (erpShopInfo != null) 
                return erpShopInfo;
        }

        return null;
    }

    public void updateLoginShop(ErpShopInfo erpShopInfo) {
        String sessionKey = getSessionKey();
        if (StringUtils.isNotEmpty(sessionKey)) {
            try {
                String erpShopInfoJson = objectMapper.writeValueAsString(erpShopInfo);
                JedisUtils.set(sessionKey, erpShopInfoJson, LoginConstants.SESSION_TIMEOUT);
            } catch (JsonProcessingException e) {
                LOGGER.error("更新登录信息时，json序列化异常！", e);
            }
        }
    }
    
    public String getSessionKey() {
        String shopToken = request.getHeader(LoginConstants.SHOP_TOKEN);
        LOGGER.info("getSessionKey  | shopToken = {}", shopToken);
        if (StringUtils.isNotEmpty(shopToken)) {
            String[] tokens = shopToken.split("-");
            String sessionKey = LoginConstants.SESSION_PREFIX + tokens[0];
            return sessionKey;
        }
        return null;
    }
    
}
