package com.yunnex.ops.erp.modules.store.pay.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;

/**
 * 银联支付开通资料DAO接口
 * @author yunnex
 * @version 2017-12-09
 */
@MyBatisDao
public interface ErpStorePayUnionpayDao extends CrudDao<ErpStorePayUnionpay> {
    
    ErpStorePayUnionpay getPayInfo(@Param("erpStoreInfoId") String erpStoreInfoId, @Param("shopId") String shopId, 
                                   @Param("isMain") Integer isMain);
	
}