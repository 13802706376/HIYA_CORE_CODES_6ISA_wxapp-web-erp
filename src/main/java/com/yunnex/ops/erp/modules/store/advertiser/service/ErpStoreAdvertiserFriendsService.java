package com.yunnex.ops.erp.modules.store.advertiser.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.advertiser.dao.ErpStoreAdvertiserFriendsDao;
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserFriends;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreInfoDao;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 朋友圈广告主开通资料Service
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreAdvertiserFriendsService extends CrudService<ErpStoreAdvertiserFriendsDao, ErpStoreAdvertiserFriends> {
   
    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpStoreInfoDao erpStoreInfoDao;
    
    @Override
	public ErpStoreAdvertiserFriends get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreAdvertiserFriends> findList(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends) {
		return super.findList(erpStoreAdvertiserFriends);
	}

    @Override
	public Page<ErpStoreAdvertiserFriends> findPage(Page<ErpStoreAdvertiserFriends> page, ErpStoreAdvertiserFriends erpStoreAdvertiserFriends) {
		return super.findPage(page, erpStoreAdvertiserFriends);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends) {
		super.save(erpStoreAdvertiserFriends);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends) {
		super.delete(erpStoreAdvertiserFriends);
	}
	
	public ErpStoreAdvertiserFriends getAdvInfo(String erpStoreInfoId, Integer isMain) {
	    ErpShopInfo loginShop = shopUtils.getLoginShop();
	    return dao.getAdvInfo(erpStoreInfoId, loginShop.getId(), isMain);
	}

	@Transactional(readOnly = false)
    public void saveInfo(ErpStoreAdvertiserFriends advInfo) {
	    // 检查是否可编辑
	    if (advInfo != null && StringUtils.isNotBlank(advInfo.getId())) {
	        ErpStoreAdvertiserFriends friends = super.get(advInfo.getId());
	        StoreUtils.checkEditable(friends.getAuditStatus());
	    }
        super.save(advInfo);
        // 更新门店的朋友圈广告主外键
        if (advInfo != null) {
            erpStoreInfoDao.updateFK("advertiser_friends_id", advInfo.getId(), advInfo.getErpStoreInfoId());
        }
    }
	
}