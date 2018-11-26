package com.yunnex.ops.erp.modules.qualify.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.qualify.entity.ErpShopPayQualify;

/**
 * 商户支付资质DAO接口
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpShopPayQualifyDao extends CrudDao<ErpShopPayQualify> {

    List<String> findPayQualifyList(@Param("shopId") String shopId);
}