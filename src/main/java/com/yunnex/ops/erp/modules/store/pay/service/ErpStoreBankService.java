package com.yunnex.ops.erp.modules.store.pay.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;
import com.yunnex.ops.erp.modules.store.pay.dao.ErpStoreBankDao;
import com.yunnex.ops.erp.modules.store.pay.dao.ErpStorePayUnionpayDao;
import com.yunnex.ops.erp.modules.store.pay.dao.ErpStorePayWeixinDao;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStoreBank;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayWeixin;

/**
 * 银行信息Service
 * 
 * @author yunnex
 * @version 2017-12-15
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreBankService extends CrudService<ErpStoreBankDao, ErpStoreBank> {
    
    @Autowired
    private ShopUtils shopUtils;
    
    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;

    @Autowired
    private ErpStorePayWeixinDao erpStorePayWeixinDao;

    @Autowired
    private ErpStorePayUnionpayDao erpStorePayUnionpayDao;

    @Override
	public ErpStoreBank get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreBank> findList(ErpStoreBank erpStoreBank) {
		return super.findList(erpStoreBank);
	}

    @Override
	public Page<ErpStoreBank> findPage(Page<ErpStoreBank> page, ErpStoreBank erpStoreBank) {
		return super.findPage(page, erpStoreBank);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreBank erpStoreBank) {
		super.save(erpStoreBank);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreBank erpStoreBank) {
		super.delete(erpStoreBank);
	}
	
	public Page<ErpStoreBank> findBankNames(Page<ErpStoreBank> page, ErpStoreBank erpStoreBank) {
	    erpStoreBank.setPage(page);
	    return page.setList(dao.findBankNames(erpStoreBank));
	}
	
	public Page<ErpStoreBank> findShopBanksByCreditCardNo(Page<ErpStoreBank> page, ErpStoreBank erpStoreBank) {
	    ErpShopInfo loginShop = shopUtils.getLoginShop();
	    erpStoreBank.setShopInfoId(loginShop.getId());
	    erpStoreBank.setPage(page);
	    return page.setList(dao.findShopBanksByCreditCardNo(erpStoreBank));
	}
	
    /**
     * 保存银行信息
     * 
     * @date 2018年4月4日
     */
    @Transactional(readOnly = false)
    public String saveBankInfo(ErpStoreBank erpStoreBank) {
        logger.info("保存银行信息入参：{}", JSON.toJSON(erpStoreBank));
        if (erpStoreBank != null && StringUtils.isNotBlank(erpStoreBank.getErpStoreInfoId())) {
            // 初始为对公账户
            Integer accountType = StoreConstants.BANK_DEFAULT_ACCOUNT_TYPE;
            ErpStoreBank bankDB = null;
            String retBankId = null, wxBankId = null, unionBankId = null;
            if (StringUtils.isNotBlank(erpStoreBank.getId())) {
                retBankId = erpStoreBank.getId();
                bankDB = super.get(erpStoreBank.getId());
                if (bankDB != null && bankDB.getAccountType() != null) {
                    accountType = bankDB.getAccountType();
                }
            }
            erpStoreBank.setAccountType(accountType);
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoDao.get(erpStoreBank.getErpStoreInfoId());
            // 保存微信支付信息
            ErpStorePayWeixin erpStorePayWeixin = this.erpStorePayWeixinDao.get(erpStoreInfo.getWeixinPayId());
            // 保存银联支付信息
            ErpStorePayUnionpay erpStorePayUnionpay = this.erpStorePayUnionpayDao.get(erpStoreInfo.getUnionpayId());
            if (logger.isInfoEnabled()) {
                logger.info("门店ID={},微信支付ID={},银联支付ID={}", erpStoreInfo != null ? erpStoreInfo.getId() : null,
                                erpStorePayWeixin != null ? erpStorePayWeixin.getId() : null,
                                erpStorePayUnionpay != null ? erpStorePayUnionpay.getId() : null);
            }
            if (null == erpStorePayWeixin) {
                // 从小程序创建门店（此时没有微信和银联信息），录入银行信息时，需要将录入的银行信息分别复制给微信和银联
                erpStoreBank.setIsNewRecord(false);
                erpStoreBank.preInsert();
                dao.insert(erpStoreBank);
                wxBankId = erpStoreBank.getId();
                erpStorePayWeixin = new ErpStorePayWeixin();
                erpStorePayWeixin.setIsNewRecord(false);
                erpStorePayWeixin.preInsert();
                erpStorePayWeixin.setBankId(erpStoreBank.getId());
                this.erpStorePayWeixinDao.insert(erpStorePayWeixin);
                // 更新门店的微信支付关联信息
                erpStoreInfo.preUpdate();
                erpStoreInfo.setWeixinPayId(erpStorePayWeixin.getId());
                this.erpStoreInfoDao.update(erpStoreInfo);
                logger.info("保存微信支付资料成功！wxId = {}, bankId={}", erpStorePayWeixin.getId(), erpStoreBank.getId());
            } else {
                // 未锁定时，更新银行信息
                if (erpStorePayWeixin.getAuditStatus() != null) {
                    StoreUtils.checkEditable(erpStorePayWeixin.getAuditStatus());
                    super.save(erpStoreBank);
                }
            }

            if (null == erpStorePayUnionpay) {
                // 从小程序创建门店（此时没有微信和银联信息），录入银行信息时，需要将录入的银行信息分别复制给微信和银联
                erpStoreBank.setIsNewRecord(false);
                erpStoreBank.preInsert();
                dao.insert(erpStoreBank);
                unionBankId = erpStoreBank.getId();
                erpStorePayUnionpay = new ErpStorePayUnionpay();
                erpStorePayUnionpay.setIsNewRecord(false);
                erpStorePayUnionpay.preInsert();
                erpStorePayUnionpay.setBankId(erpStoreBank.getId());
                this.erpStorePayUnionpayDao.insert(erpStorePayUnionpay);
                // 更新门店的银联支付关联信息
                erpStoreInfo.preUpdate();
                erpStoreInfo.setUnionpayId(erpStorePayUnionpay.getId());
                this.erpStoreInfoDao.update(erpStoreInfo);
                logger.info("保存银联支付资料成功！unionpayId = {}, bankId={}", erpStorePayUnionpay.getId(), erpStoreBank.getId());
            } else {
                // 未锁定时，更新银行信息
                if (erpStorePayUnionpay.getAuditStatus() != null) {
                    StoreUtils.checkEditable(erpStorePayUnionpay.getAuditStatus());
                    super.save(erpStoreBank);
                }
            }

            if (erpStorePayWeixin != null && wxBankId != null) {
                return wxBankId;
            }
            if (erpStorePayUnionpay != null && unionBankId != null) {
                return unionBankId;
            }
            return retBankId;
        } else {
            throw new RuntimeException("参数错误！");
        }
    }

}
