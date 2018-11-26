package com.yunnex.ops.erp.modules.order.service;

import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.yunnex.ops.erp.modules.good.category.entity.ErpGoodCategory;
import com.yunnex.ops.erp.modules.good.category.service.ErpGoodCategoryService;
import com.yunnex.ops.erp.modules.good.entity.ErpGoodInfo;
import com.yunnex.ops.erp.modules.good.service.ErpGoodInfoApiService;
import com.yunnex.ops.erp.modules.good.service.ErpGoodInfoService;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoApiService;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;

@Service
public class ErpOrderOriginalInfoApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErpOrderOriginalInfoApiService.class);

    @Autowired
    private ErpOrderOriginalInfoService erpOrderOriginalInfoService;

    @Autowired
    private ErpOrderOriginalGoodService erpOrderOriginalGoodService;

    @Autowired
    private ErpShopInfoApiService erpShopInfoApiService;

    @Autowired
    private ErpGoodCategoryService erpGoodCategoryService;

    @Autowired
    private ErpShopInfoService erpShopInfoService;

    @Autowired
    private ErpGoodInfoService erpGoodInfoService;

    @Autowired
    private ErpGoodInfoApiService erpGoodInfoApiService;

    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    @Value("${api_order_info_url}")
    private String API_ORDER_INFO_URL;

    @Transactional(readOnly = false)
    public boolean saveSingleOrder(String jsonStr) {
        try {
            JSONObject orderJsonObject = JSONObject.parseObject(jsonStr);
            // 只查询未作废的订单来更新，如果不存在就增加
            ErpOrderOriginalInfo orderInfo = erpOrderOriginalInfoService.getUnCancelOrderByOrderNo(orderJsonObject.getString("order_number"), 0);
            insertOrUpdateOrder(orderJsonObject, orderInfo == null ? null : orderInfo.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    private void insertOrUpdateOrder(JSONObject orderJsonObject, String orderId) {
        ErpOrderOriginalInfo erpOrderOriginalInfo = new ErpOrderOriginalInfo();
        erpOrderOriginalInfo.setId(orderId);    // 如果存在ID，则更新
        erpOrderOriginalInfo.setOrderNumber(orderJsonObject.getString("order_number"));
        erpOrderOriginalInfo.setBuyDate(orderJsonObject.getDate("buy_date"));
        erpOrderOriginalInfo.setContactName(orderJsonObject.getString("contact_name"));
        erpOrderOriginalInfo.setSource(orderJsonObject.getString("source"));
        erpOrderOriginalInfo.setContactNumber(orderJsonObject.getString("contact_phone"));
        erpOrderOriginalInfo.setShopAbbreviation(orderJsonObject.getString("shop_abbreviation"));
        erpOrderOriginalInfo.setRealPrice(orderJsonObject.getLong("real_price"));
        erpOrderOriginalInfo.setShopName(orderJsonObject.getString("shop_name"));
        erpOrderOriginalInfo.setShopNumber(orderJsonObject.getString("shop_number"));
        erpOrderOriginalInfo.setShopExtensionId(orderJsonObject.getString("shop_extension_id"));
        erpOrderOriginalInfo.setCreateAt(orderJsonObject.getDate("create_date"));
        erpOrderOriginalInfo.setPayDate(orderJsonObject.getDate("pay_date"));
        erpOrderOriginalInfo.setShopId(orderJsonObject.getString("shop_extension_id"));
        erpOrderOriginalInfo.setOrderStatus(orderJsonObject.getInteger("order_status"));
        erpOrderOriginalInfo.setOrderType(orderJsonObject.getInteger("order_type"));
        erpOrderOriginalInfo.setPromotePhone(orderJsonObject.getString("promote_phone"));
        erpOrderOriginalInfo.setPromoteContact(orderJsonObject.getString("promote_contact"));
        erpOrderOriginalInfo.setSalePerson(orderJsonObject.getString("sale_person"));
        erpOrderOriginalInfo.setCancel(0);
        erpShopInfoApiService.isNew(orderJsonObject.getString("shop_extension_id"));
        erpOrderOriginalInfoService.save(erpOrderOriginalInfo);

        // 已经同步过的订单下面的商品数据，不会再改变；如果要改变，需要先作废订单；
        if (orderId != null) {
            return;
        }
        
        JSONArray goods = orderJsonObject.getJSONArray("goods");
        if (goods != null && !goods.isEmpty()) {
            for (int i = 0; i < goods.size(); i++) {
                JSONObject good = goods.getJSONObject(i);
                ErpOrderOriginalGood erpOrderOriginalGood = new ErpOrderOriginalGood();
                erpOrderOriginalGood.setOrderId(erpOrderOriginalInfo.getId());
                erpOrderOriginalGood.setGoodName(good.getString("name"));
                erpOrderOriginalGood.setGoodId(good.getLong("id"));
                erpOrderOriginalGood.setRealPrice(good.getLong("real_price"));
                erpOrderOriginalGood.setNum(good.getIntValue("num"));
                erpOrderOriginalGood.setPendingNum(good.getIntValue("num"));
                erpOrderOriginalGood.setProcessNum(0);
                erpOrderOriginalGood.setFinishNum(0);
                ErpGoodInfo erpGoodInfo = erpGoodInfoService.getDetail(good.getString("id"));
                if (null == erpGoodInfo) {
                    erpGoodInfoApiService.sync();
                }
                ErpGoodCategory erpGoodCategory = erpGoodCategoryService.getByGoodId(good.getLong("id"));
                if (null != erpGoodCategory) {
                    erpOrderOriginalGood.setGoodTypeId(Long.valueOf(erpGoodCategory.getId()));
                    erpOrderOriginalGood.setGoodTypeName(erpGoodCategory.getName());
                }
                erpOrderOriginalGoodService.save(erpOrderOriginalGood);
            }
        }
    }

    @Transactional(readOnly = false)
    public boolean syncBatchOrder(String startAt, String endAt) {
        singleThreadExecutor.submit(new SyncOrderThread(startAt, endAt));
        return true;
    }

    public class SyncOrderThread implements Runnable {

        private String startAt;
        private String endAt;

        public SyncOrderThread(String startAt, String endAt) {
            this.startAt = startAt;
            this.endAt = endAt;
        }

        @Override
        public void run() {
            try {
                String reqUrl = String.format(API_ORDER_INFO_URL, URLEncoder.encode(startAt, "utf-8"), URLEncoder.encode(endAt, "utf-8"));
                // 本地和远程订单数量相同也要更新，因为可能修改了部分信息
                while (true) {
                    JSONObject syncObject = getOrderDetailFromDiTui(reqUrl);
                    if(null!=syncObject)
                    {
	                    JSONObject syncData = syncObject.getJSONObject("data");
	                    JSONArray results = syncData.getJSONArray("results");
                        if (null == results || results.isEmpty()) {
	                        break;
	                    }
	                    for (int i = 0; i < results.size(); i++) {
	                        saveSingleOrder(results.getJSONObject(i).toJSONString());
	                    }
	                    reqUrl = syncData.getString("next");
	                    if (StringUtils.isEmpty(reqUrl) || !syncData.getBooleanValue("has_next")) {
	                        break;
	                    }
                }else
                    {
                    	break;
                    }}
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        private JSONObject getOrderDetailFromDiTui(String reqUrl) {
            String resStr = HttpUtil.sendHttpGetReqToServer(reqUrl);
            if (StringUtils.isNotEmpty(resStr)) {
                return JSONObject.parseObject(resStr);
            }
            return null;
        }

    }

}
