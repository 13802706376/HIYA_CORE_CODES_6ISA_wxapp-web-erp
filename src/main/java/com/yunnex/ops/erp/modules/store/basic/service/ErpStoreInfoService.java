package com.yunnex.ops.erp.modules.store.basic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.utils.AESUtil;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoList;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoParam;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayWeixin;

/**
 * 门店基本信息Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreInfoService extends CrudService<ErpStoreInfoDao, ErpStoreInfo> {

    @Autowired
    private ErpShopInfoService erpShopInfoService;

    @Autowired
    private ShopUtils shopUtils;

    @Override
    public ErpStoreInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpStoreInfo> findList(ErpStoreInfo erpStoreInfo) {
        return super.findList(erpStoreInfo);
    }

    @Override
    public Page<ErpStoreInfo> findPage(Page<ErpStoreInfo> page, ErpStoreInfo erpStoreInfo) {
        return super.findPage(page, erpStoreInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpStoreInfo erpStoreInfo) {
        super.save(erpStoreInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpStoreInfo erpStoreInfo) {
        super.delete(erpStoreInfo);
    }

    public ErpStoreInfoParam getErpStoreInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getErpStoreInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }

    public Page<ErpStoreInfoList> getStoreList(Page<ErpStoreInfoList> page, ErpStoreInfoList erpStoreInfoList) {
        erpStoreInfoList.setPage(page);
        page.setList(dao.getStoreList(erpStoreInfoList));
        return page;
    }

    public Map<String, Integer> getFinishStatus(String erpStoreInfoId) {
        return dao.getFinishStatus(erpStoreInfoId);
    }

    /**
     * 保存门店基本信息
     * 
     * @date 2018年4月3日
     */
    @Transactional(readOnly = false)
    public ErpStoreInfo saveBasicInfo(ErpStoreInfo erpStoreInfo) {
        logger.info("saveBasicInfo start | erpStoreInfo={}",JSON.toJSONString(erpStoreInfo));
        if (erpStoreInfo == null) {
            throw new RuntimeException("参数错误！");
        }
        // 置空前端传输的 主门店isMain 的值，避免前端修改主门店标识
        erpStoreInfo.setIsMain(null);
        // 检查是否可编辑
        if (StringUtils.isNotBlank(erpStoreInfo.getId())) {
            ErpStoreInfo dbStore = dao.get(erpStoreInfo.getId());
            StoreUtils.checkEditable(dbStore.getAuditStatus());
        } else {
            // 如果是第1家门店, 默认设置为总店
            ErpShopInfo loginShop = shopUtils.getLoginShop();
            loginShop = erpShopInfoService.get(loginShop.getId());
            erpStoreInfo.setIsMain(loginShop.getStoreCount() == 0 ? StoreConstants.IS_MAIN_YES : StoreConstants.IS_MAIN_NO);
            // 设置商户ID
            erpStoreInfo.setShopInfoId(loginShop.getId());
            // 新增店铺，商户店铺数量+1
            loginShop.setStoreCount(loginShop.getStoreCount() + 1);
            erpShopInfoService.save(loginShop); // 更新到db中
            shopUtils.updateLoginShop(loginShop); // 更新到session中
        }
        // 保存门店基本信息
        super.save(erpStoreInfo);
        logger.info("saveBasicInfo end");
        return erpStoreInfo;
    }

    /**
     * 查询门店基本信息
     *
     * @date 2018年4月9日
     */
    public ErpStoreInfoParam getBasic(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getBasic(erpStoreInfoId, loginShop.getId(), isMain);
    }

    /**
     * 查询门店证件信息
     *
     * @date 2018年4月9日
     */
    public ErpStoreInfoParam getDocuments(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getDocuments(erpStoreInfoId, loginShop.getId(), isMain);
    }

    /**
     * 查询账号信息
     *
     * @date 2018年4月9日
     */
    public ErpStoreInfoParam getAccounts(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        ErpStoreInfoParam resParam = dao.getAccounts(erpStoreInfoId, loginShop.getId(), isMain);
        // 密码解密，明文显示
        if (resParam != null) {
            ErpStorePayWeixin weixin = resParam.getErpStorePayWeixin();
            if (weixin != null) {
                // 公众号密码解密
                String accountPassword = weixin.getPublicAccountPassword();
                if (StringUtils.isNotBlank(accountPassword)) {
                    weixin.setPublicAccountPassword(AESUtil.decrypt(accountPassword));
                }
                // 邮箱密码解密
                String emailPassword = weixin.getEmailPassword();
                if (StringUtils.isNotBlank(emailPassword)) {
                    weixin.setEmailPassword(AESUtil.decrypt(emailPassword));
                }
                // 是否开通公招 (公众号登录账号和公众号登录密码非空）或公众号APPID非空，则为是，否则为否)
                weixin.setOpenFlag(CommonConstants.Sign.NO);
                if ((StringUtils.isNotBlank(accountPassword) && StringUtils.isNotBlank(weixin.getPublicAccountNo())) || StringUtils
                                .isNotBlank(weixin.getPublicAccountAppid())) {
                    weixin.setOpenFlag(CommonConstants.Sign.YES);
                }
            }
            if (resParam.getErpStoreAdvertiserWeibo() != null && StringUtils.isNotBlank(resParam.getErpStoreAdvertiserWeibo().getAccountPassword())) {
                resParam.getErpStoreAdvertiserWeibo().setAccountPassword(AESUtil.decrypt(resParam.getErpStoreAdvertiserWeibo().getAccountPassword()));
            }
        }
        return resParam;
    }

    /**
     * 修改页面编辑标识
     * 
     * @date 2018年4月10日
     */
    @Transactional(readOnly = false)
    public void updatePageEditTag(String erpStoreInfoId, Integer pageEditTag) {
        dao.updatePageEditTag(erpStoreInfoId, pageEditTag);
    }
    
    /**
     * 临时功能，初始化加密密码，使用后删除
     *
     * @date 2018年4月13日
     */
    @Transactional(readOnly = false)
    public void updatePwd(Integer type) {
        boolean aesType = type == Integer.valueOf(0) ? false : true;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 微信支付密码
        paramMap.put("id", "id");
        paramMap.put("pwd", "public_account_password");
        paramMap.put("tableName", "erp_store_pay_weixin");
        List<Map<String, String>> list = dao.queryPassword(paramMap);
        if (list != null && !list.isEmpty()) {
            for (Map<String, String> map : list) {
                if (StringUtils.isNotBlank(map.get("pwd"))) {
                    map.put("pwd", aesType ? AESUtil.encrypt(map.get("pwd")) : AESUtil.decrypt(map.get("pwd")));
                }
            }
        }
        paramMap.put("list", list);
        dao.updatePwd(paramMap);
        // 微博密码
        paramMap.put("pwd", "account_password");
        paramMap.put("tableName", "erp_store_advertiser_weibo");
        list = dao.queryPassword(paramMap);
        if (list != null && !list.isEmpty()) {
            for (Map<String, String> map : list) {
                if (StringUtils.isNotBlank(map.get("pwd"))) {
                    map.put("pwd", aesType ? AESUtil.encrypt(map.get("pwd")) : AESUtil.decrypt(map.get("pwd")));
                }
            }
        }
        paramMap.put("list", list);
        dao.updatePwd(paramMap);
        // 朋友圈密码
        paramMap.put("pwd", "account_password");
        paramMap.put("tableName", "erp_store_advertiser_friends");
        list = dao.queryPassword(paramMap);
        if (list != null && !list.isEmpty()) {
            for (Map<String, String> map : list) {
                if (StringUtils.isNotBlank(map.get("pwd"))) {
                    map.put("pwd", aesType ? AESUtil.encrypt(map.get("pwd")) : AESUtil.decrypt(map.get("pwd")));
                }
            }
        }
        paramMap.put("list", list);
        dao.updatePwd(paramMap);
    }

    /**
     * 根据掌贝id获取主门店信息
     *
     * @param zhangbeiId
     * @return
     * @date 2018年8月6日
     * @author linqunzhi
     */
    public ErpStoreInfo getMainByZhangbeiId(String zhangbeiId) {
        logger.info("getMainByZhangbeiId start | zhangbeiId={}", zhangbeiId);
        ErpStoreInfo result = dao.getMainByZhangbeiId(zhangbeiId);
        logger.info("getMainByZhangbeiId end");
        return result;
    }
}
