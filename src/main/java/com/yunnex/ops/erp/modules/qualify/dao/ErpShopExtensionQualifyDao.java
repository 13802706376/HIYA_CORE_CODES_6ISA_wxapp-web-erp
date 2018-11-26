package com.yunnex.ops.erp.modules.qualify.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.qualify.entity.ErpShopExtensionQualify;

/**
 * 商户推广资质DAO接口
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpShopExtensionQualifyDao extends CrudDao<ErpShopExtensionQualify> {

    List<String> findExtensionQualifyList(@Param("shopId") String shopId);
	
}