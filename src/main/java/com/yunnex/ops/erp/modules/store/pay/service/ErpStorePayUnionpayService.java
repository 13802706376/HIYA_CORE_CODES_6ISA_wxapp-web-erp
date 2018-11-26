package com.yunnex.ops.erp.modules.store.pay.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;
import com.yunnex.ops.erp.modules.store.pay.dao.ErpStorePayUnionpayDao;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;

/**
 * 银联支付开通资料Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStorePayUnionpayService extends CrudService<ErpStorePayUnionpayDao, ErpStorePayUnionpay> {

    @Autowired
    private ShopUtils shopUtils;

    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;
    
    @Override
	public ErpStorePayUnionpay get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStorePayUnionpay> findList(ErpStorePayUnionpay erpStorePayUnionpay) {
		return super.findList(erpStorePayUnionpay);
	}

    @Override
	public Page<ErpStorePayUnionpay> findPage(Page<ErpStorePayUnionpay> page, ErpStorePayUnionpay erpStorePayUnionpay) {
		return super.findPage(page, erpStorePayUnionpay);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStorePayUnionpay erpStorePayUnionpay) {
		super.save(erpStorePayUnionpay);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStorePayUnionpay erpStorePayUnionpay) {
		super.delete(erpStorePayUnionpay);
	}
	
	public ErpStorePayUnionpay getPayInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getPayInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }
	
    /**
     * 保存银联支付信息
     * 
     * @date 2018年4月8日
     */
	@Transactional(readOnly = false)
    public String saveInfo(ErpStorePayUnionpay payInfo) {
        // 检查是否可编辑
        if (payInfo != null && StringUtils.isNotBlank(payInfo.getErpStoreInfoId())) {
	        ErpStorePayUnionpay unionpay = super.get(payInfo.getId());
            StoreUtils.checkEditable(unionpay != null ? unionpay.getAuditStatus() : StoreConstants.STATUS_UNCOMMITTED);
            super.save(payInfo);
            // 更新门店基本信息
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoDao.get(payInfo.getErpStoreInfoId());
            erpStoreInfo.setUnionpayId(payInfo.getId());
            erpStoreInfo.preUpdate();
            this.erpStoreInfoDao.update(erpStoreInfo);
            return payInfo.getId();
        } else {
            throw new RuntimeException("参数错误！");
	    }
	}
	
}