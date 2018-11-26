package com.yunnex.ops.erp.modules.shop.service;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.common.utils.ResponeUtil;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;

@Service
@Transactional(readOnly = true)
public class ErpShopInfoApiService {

    private final int PAGE_SIZE = 30;

    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    private ErpShopInfoService erpShopInfoService;

    @Value("${api_agent_info_url}")
    private String API_AGENT_INFO_URL;

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Transactional(readOnly = false)
    public boolean syncAll() {
        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                int page = 1;
                while(true) {
                    try {
                        JSONObject data = getDataFromOem(null, page++, PAGE_SIZE);
                        if (ResponeUtil.isResponeValid(data)) {
                            JSONArray shops = data.getJSONArray("shops");
                            if (null != shops && !shops.isEmpty()) {
                                for (int i = 0; i < shops.size(); i++) {
                                    ErpShopInfo erpShopInfo = convertFromJsonObject(shops.getJSONObject(i));
                                    if (StringUtils.isEmpty(erpShopInfo.getZhangbeiId())) {
                                        continue;
                                    }
                                    if (erpShopInfoService.countShopByZhangbeiId(erpShopInfo.getZhangbeiId()) > 0) {
                                        erpShopInfo.setUpdateDate(new Date());
                                        erpShopInfoService.updateByZhangbeiId(erpShopInfo);
                                    } else {
                                        erpShopInfoService.save(erpShopInfo);
                                    }
                                }
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        break;
                    }
                }
            }
        });
        return true;
    }

    /**
     * 根据掌贝id查询是否是新商户，现在本地数据库查一遍，如果没有再去OEM拉取一遍插入数据库
     *
     * @param zhangBeiId
     * @return
     * @date 2017年10月25日
     * @author zjq
     */
    @Transactional(readOnly = false)
    public boolean isNew(String zhangBeiId) {
        if (erpShopInfoService.countShopByZhangbeiId(zhangBeiId) > 0) {
            return false;
        }
        JSONObject data = getDataFromOem(new String[] {zhangBeiId}, 1, PAGE_SIZE);
        JSONArray shops = data.getJSONArray("shops");
        if (null != shops && shops.size() > 0) {
            ErpShopInfo shopInfoFromOem = convertFromJsonObject(shops.getJSONObject(0));
            erpShopInfoService.save(shopInfoFromOem);
            return false;
        }
        return true;
    }
    /**
     * 根据掌贝id查询是否是新商户，现在本地数据库查一遍，如果没有再去OEM拉取一遍插入数据库
     *
     * @param zhangBeiId
     * @return
     * @date 2017年10月25日
     * @author zjq
     */
    @Transactional(readOnly = false)
    public boolean isShopInputPieces(String zhangBeiId) {
        if (erpShopInfoService.countShopByZhangbeiId(zhangBeiId) > 0) {
            return true;
        }
        JSONObject data = getDataFromOem(new String[] {zhangBeiId}, 1, PAGE_SIZE);
        JSONArray shops = data.getJSONArray("shops");
        if (null != shops && !shops.isEmpty()) {
            ErpShopInfo shopInfoFromOem = convertFromJsonObject(shops.getJSONObject(0));
            erpShopInfoService.save(shopInfoFromOem);
            return true;
        }
        return false;
    }

    private JSONObject getDataFromOem(String[] zhangBeiIds, Integer page, Integer pageSize) {
        JSONObject reqObject = new JSONObject();
        if (ArrayUtils.isEmpty(zhangBeiIds)) {
            if (null == page || null == pageSize) {
                return new JSONObject();
            }
            reqObject.put("page", page);
            reqObject.put("pageSize", pageSize);

        } else {
            reqObject.put("page", page);
            reqObject.put("pageSize", pageSize);
            reqObject.put("phoneNumbers", Arrays.asList(zhangBeiIds));
        }
        String resStr = HttpUtil.sendHttpPostReqToServerByReqbody(API_AGENT_INFO_URL,
                        reqObject.toJSONString(), "application/json");
        if (StringUtils.isNotEmpty(resStr)) {
            return JSONObject.parseObject(resStr);
        }
        return new JSONObject();
    }

    private ErpShopInfo convertFromJsonObject(JSONObject shop) {
        ErpShopInfo erpShopInfo = new ErpShopInfo();
        erpShopInfo.setNumber(shop.getString("nubmer"));
        erpShopInfo.setName(shop.getString("name"));
        erpShopInfo.setAbbreviation(shop.getString("abbreivation"));
        erpShopInfo.setIndustryType(shop.getString("industryType"));
        erpShopInfo.setAddress(shop.getString("address"));
        erpShopInfo.setContactEmail(shop.getString("contactEmail"));
        erpShopInfo.setContactName(shop.getString("contactName"));
        erpShopInfo.setContactPhone(shop.getString("contactPhone"));
        erpShopInfo.setServiceProvider(shop.getString("serviceProvider"));
        erpShopInfo.setServiceProviderPhone(shop.getString("serviceProviderPhone"));
        erpShopInfo.setZhangbeiId(shop.getString("zhangbeiId"));
        return erpShopInfo;
    }
}
