package com.yunnex.ops.erp.modules.store.advertiser.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.utils.AESUtil;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.advertiser.dao.ErpStoreAdvertiserWeiboDao;
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserWeibo;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 微博广告主开通资料Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreAdvertiserWeiboService extends CrudService<ErpStoreAdvertiserWeiboDao, ErpStoreAdvertiserWeibo> {

    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;
    
    @Override
	public ErpStoreAdvertiserWeibo get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreAdvertiserWeibo> findList(ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo) {
		return super.findList(erpStoreAdvertiserWeibo);
	}

    @Override
	public Page<ErpStoreAdvertiserWeibo> findPage(Page<ErpStoreAdvertiserWeibo> page, ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo) {
		return super.findPage(page, erpStoreAdvertiserWeibo);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo) {
		super.save(erpStoreAdvertiserWeibo);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo) {
		super.delete(erpStoreAdvertiserWeibo);
	}
	
	public ErpStoreAdvertiserWeibo getAdvInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getAdvInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }
	
    /**
     * 保存微博广告信息
     *
     * @date 2018年4月8日
     */
	@Transactional(readOnly = false)
    public String saveInfo(ErpStoreAdvertiserWeibo advInfo) {
        // 检查是否可编辑
        if (advInfo != null && StringUtils.isNotBlank(advInfo.getErpStoreInfoId())) {
            ErpStoreAdvertiserWeibo weibo = super.get(advInfo.getId());
            StoreUtils.checkEditable(weibo != null ? weibo.getAuditStatus() : StoreConstants.STATUS_UNCOMMITTED);
            // 密码加密
            if (StringUtils.isNotBlank(advInfo.getAccountPassword())) {
                advInfo.setAccountPassword(AESUtil.encrypt(advInfo.getAccountPassword()));
            }
            // 保存微博广告信息
            super.save(advInfo);
            // 更新门店基本信息
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoDao.get(advInfo.getErpStoreInfoId());
            erpStoreInfo.setAdvertiserWeiboId(advInfo.getId());
            erpStoreInfo.preUpdate();
            this.erpStoreInfoDao.update(erpStoreInfo);
            return advInfo.getId();
        } else {
            throw new RuntimeException("参数错误！");
        }
	}
	
}