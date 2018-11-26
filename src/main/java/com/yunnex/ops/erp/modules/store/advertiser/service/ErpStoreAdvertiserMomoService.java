package com.yunnex.ops.erp.modules.store.advertiser.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.advertiser.dao.ErpStoreAdvertiserMomoDao;
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserMomo;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 陌陌广告主开通资料Service
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreAdvertiserMomoService extends CrudService<ErpStoreAdvertiserMomoDao, ErpStoreAdvertiserMomo> {
    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;
    
    @Override
	public ErpStoreAdvertiserMomo get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreAdvertiserMomo> findList(ErpStoreAdvertiserMomo erpStoreAdvertiserMomo) {
		return super.findList(erpStoreAdvertiserMomo);
	}

    @Override
	public Page<ErpStoreAdvertiserMomo> findPage(Page<ErpStoreAdvertiserMomo> page, ErpStoreAdvertiserMomo erpStoreAdvertiserMomo) {
		return super.findPage(page, erpStoreAdvertiserMomo);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreAdvertiserMomo erpStoreAdvertiserMomo) {
		super.save(erpStoreAdvertiserMomo);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreAdvertiserMomo erpStoreAdvertiserMomo) {
		super.delete(erpStoreAdvertiserMomo);
	}
	
	public ErpStoreAdvertiserMomo getAdvInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getAdvInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }
	
	@Transactional(readOnly = false)
    public void saveInfo(ErpStoreAdvertiserMomo advInfo) {
	    // 检查是否可编辑
        if (advInfo != null && StringUtils.isNotBlank(advInfo.getId())) {
            ErpStoreAdvertiserMomo momo = super.get(advInfo.getId());
            StoreUtils.checkEditable(momo.getAuditStatus());
        }
        super.save(advInfo);
	    // 更新门店的陌陌广告主外键
        if (advInfo != null) {
            erpStoreInfoDao.updateFK("advertiser_momo_id", advInfo.getId(), advInfo.getErpStoreInfoId());
        }
    }
	
}