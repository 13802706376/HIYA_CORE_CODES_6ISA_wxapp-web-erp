package com.yunnex.ops.erp.modules.good.service;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.modules.good.entity.ErpGoodInfo;

@Service
public class ErpGoodInfoApiService {

    @Autowired
    private ErpGoodInfoService erpGoodInfoService;

    @Value("${api_good_info_url}")
    private String API_GOOD_INFO_URL;

    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * 地推系统同步商品信息
     *
     * @return
     * @date 2017年10月23日
     * @author huanghaidong
     */
    @Transactional(readOnly = false)
    public boolean sync() {
        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqUrl = API_GOOD_INFO_URL;
                    while (true) {
                        JSONObject goodsObject = getGoodsData(reqUrl);
                        if (goodsObject.getIntValue("errcode") == 200) {
                            JSONObject data = goodsObject.getJSONObject("data");
                            JSONArray goodsArray = data.getJSONArray("results");
                            if (null != goodsArray) {
                                for (int i = 0; i < goodsArray.size(); i++) {
                                    JSONObject goods = goodsArray.getJSONObject(i);
                                    insertOrUpdateGood(goods);
                                }
                            }
                            if (!data.getBooleanValue("has_next")) {
                                break;
                            }
                            reqUrl = data.getString("next");
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Transactional(readOnly = false)
    private boolean insertOrUpdateGood(JSONObject goods) {
        try {
            
            String id = goods.getString("id");
            ErpGoodInfo erpGoodInfo = erpGoodInfoService.getDetail(id);
            if (null == erpGoodInfo) {
                erpGoodInfo = new ErpGoodInfo();
                erpGoodInfo.setId(id);
                erpGoodInfo.setIsNewRecord(true);
                erpGoodInfo.setCategoryId(0L);
                erpGoodInfo.setName(goods.getString("name"));
                erpGoodInfo.setPrice(goods.getLong("price"));
                if (goods.getIntValue("status") != 1) {
                    erpGoodInfo.setDelFlag("1");
                } else {
                    erpGoodInfo.setDelFlag("0");
                }
                erpGoodInfoService.save(erpGoodInfo);
            } else {
                erpGoodInfo.setName(goods.getString("name"));
                erpGoodInfo.setPrice(goods.getLong("price"));
                if (goods.getIntValue("status") != 1) {
                    erpGoodInfo.setDelFlag("1");
                } else {
                    erpGoodInfo.setDelFlag("0");
                }
                erpGoodInfo.setUpdateDate(new Date());
                erpGoodInfoService.updateDetail(erpGoodInfo);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public JSONObject getGoodsData(String reqUrl) {
        String resStr = HttpUtil.sendHttpGetReqToServer(reqUrl);
        if (StringUtils.isEmpty(resStr)) {
            return null;
        }
        return JSONObject.parseObject(resStr);
    }


    @Transactional(readOnly = false)
    public boolean push(String jsonStr) {
        return insertOrUpdateGood(JSONObject.parseObject(jsonStr));
    }
}
