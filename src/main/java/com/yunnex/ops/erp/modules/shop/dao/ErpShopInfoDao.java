package com.yunnex.ops.erp.modules.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;

/**
 * 商户管理DAO接口
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpShopInfoDao extends CrudDao<ErpShopInfo> {

    List<String> getDistinctdustry();

    Integer countShopByZhangbeiId(@Param("zhangbeiId") String zhangbeiId);

    int updateByZhangbeiId(@Param("erpShopInfo") ErpShopInfo erpShopInfo);

    ErpShopInfo getByZhangbeiId(@Param("zhangbeiId") String zhangbeiId);

    ErpShopInfo getByLoginName(@Param("loginName") String loginName);

    int updateIntoPiecesById(@Param("id") String id);

    List<ErpShopInfo> findshopwaiter(@Param("shopid") String shopid);


}
