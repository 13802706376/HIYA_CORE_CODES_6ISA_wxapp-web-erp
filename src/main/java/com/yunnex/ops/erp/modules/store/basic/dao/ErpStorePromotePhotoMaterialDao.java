package com.yunnex.ops.erp.modules.store.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStorePromotePhotoMaterial;

/**
 * 推广图片素材DAO接口
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@MyBatisDao
public interface ErpStorePromotePhotoMaterialDao extends CrudDao<ErpStorePromotePhotoMaterial> {

	ErpStorePromotePhotoMaterial getErpStoreInfo(@Param("erpStoreInfoId") String erpStoreInfoId,
			@Param("shopId") String shopId, @Param("isMain") Integer isMain);

	/**
	 * 根据商户id 获取商户主体门店的推广图片素材
	 * 
	 * @param shopId
	 * @return
	 */
	ErpStorePromotePhotoMaterial getStoreMainByShopId(@Param("shopId") String shopId);

    /**
     * 根据门店id 获取推广图片素材
     *
     * @param storeId
     * @return
     * @date 2018年5月29日
     * @author linqunzhi
     */
    ErpStorePromotePhotoMaterial getByStoreId(@Param("storeId") String storeId);

}