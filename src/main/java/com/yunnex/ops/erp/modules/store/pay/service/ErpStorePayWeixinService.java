package com.yunnex.ops.erp.modules.store.pay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.AESUtil;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserFriends;
import com.yunnex.ops.erp.modules.store.advertiser.service.ErpStoreAdvertiserFriendsService;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;
import com.yunnex.ops.erp.modules.store.pay.dao.ErpStorePayWeixinDao;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayWeixin;

/**
 * 微信支付开通资料Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStorePayWeixinService extends CrudService<ErpStorePayWeixinDao, ErpStorePayWeixin> {
    
    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpStoreAdvertiserFriendsService erpStoreAdvertiserFriendsService;
    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;
    
    @Override
	public ErpStorePayWeixin get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStorePayWeixin> findList(ErpStorePayWeixin erpStorePayWeixin) {
		return super.findList(erpStorePayWeixin);
	}

    @Override
	public Page<ErpStorePayWeixin> findPage(Page<ErpStorePayWeixin> page, ErpStorePayWeixin erpStorePayWeixin) {
		return super.findPage(page, erpStorePayWeixin);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStorePayWeixin erpStorePayWeixin) {
		super.save(erpStorePayWeixin);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStorePayWeixin erpStorePayWeixin) {
		super.delete(erpStorePayWeixin);
	}

    public ErpStorePayWeixin getPayInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getPayInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }

    /**
     * 保存微信支付信息
     *
     * @date 2018年4月8日
     */
    @Transactional(readOnly = false)
    public String saveInfo(ErpStorePayWeixin payInfo) {
        logger.info("saveInfo start | payInfo={}", JSON.toJSONString(payInfo));
        // 检查是否可编辑
        if (payInfo != null && StringUtils.isNotBlank(payInfo.getErpStoreInfoId())) {
            // 获取门店信息
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoDao.get(payInfo.getErpStoreInfoId());
            // 数据库支付id
            String wexinId = erpStoreInfo.getWeixinPayId();
            // 前端参数id
            String payInfoId = payInfo.getId();
            logger.info("wexinId={}|payInfoId={}", wexinId, payInfoId);
            if (StringUtils.isNotBlank(wexinId) || StringUtils.isNotBlank(payInfoId)) {
                if (StringUtils.isNotBlank(wexinId)) {
                    if (!wexinId.equals(payInfoId)) {
                        logger.info("数据库 微信支付id 和  前端参数id 不一致 ！wexinId={},payInfoId={}", wexinId, payInfoId);
                        throw new ServiceException(CommonConstants.FailMsg.PARAM);
                    }
                }
                if (StringUtils.isNotBlank(payInfoId)) {
                    if (!payInfoId.equals(wexinId)) {
                        logger.info("数据库 微信支付id 和  前端参数id 不一致 ！wexinId={},payInfoId={}", wexinId, payInfoId);
                        throw new ServiceException(CommonConstants.FailMsg.PARAM);
                    }
                }
            }
            ErpStorePayWeixin weixin = super.get(payInfoId);
            StoreUtils.checkEditable(weixin != null ? weixin.getAuditStatus() : StoreConstants.STATUS_UNCOMMITTED);
            // 保存微信支付信息
            payInfo.setProvideAccountInfo(StoreConstants.PROVIDE_PWD_YES);
            // 密码加密
            if (StringUtils.isNotBlank(payInfo.getPublicAccountPassword())) {
                payInfo.setPublicAccountPassword(AESUtil.encrypt(payInfo.getPublicAccountPassword()));
            }
            // 邮箱密码加密
            if (StringUtils.isNotBlank(payInfo.getEmailPassword())) {
                payInfo.setEmailPassword(AESUtil.encrypt(payInfo.getEmailPassword()));
            }
            super.save(payInfo);
            // 保存朋友圈广告关联信息
            ErpStoreAdvertiserFriends erpStoreAdvertiserFriends = StringUtils
                            .isNotBlank(erpStoreInfo.getAdvertiserFriendsId()) ? erpStoreAdvertiserFriendsService
                                            .get(erpStoreInfo.getAdvertiserFriendsId()) : new ErpStoreAdvertiserFriends();
            erpStoreAdvertiserFriends.setProvideAccountInfo(StoreConstants.PROVIDE_PWD_YES);
            erpStoreAdvertiserFriends.setAccountNo(payInfo.getPublicAccountNo());
            erpStoreAdvertiserFriends.setAccountPassword(payInfo.getPublicAccountPassword());
            erpStoreAdvertiserFriendsService.save(erpStoreAdvertiserFriends);
            // 更新门店基本信息
            erpStoreInfo.setWeixinPayId(payInfo.getId());
            erpStoreInfo.setAdvertiserFriendsId(erpStoreAdvertiserFriends.getId());
            erpStoreInfo.preUpdate();
            this.erpStoreInfoDao.update(erpStoreInfo);
            logger.info("saveInfo end");
            return payInfo.getId();
        } else {
            throw new RuntimeException("参数错误！");
        }
    }
	
}