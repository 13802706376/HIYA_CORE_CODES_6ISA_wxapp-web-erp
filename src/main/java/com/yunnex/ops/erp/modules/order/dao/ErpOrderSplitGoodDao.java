package com.yunnex.ops.erp.modules.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitGood;

/**
 * 
 * @author zjq
 * @date 2018年3月30日
 */
@MyBatisDao
public interface ErpOrderSplitGoodDao extends CrudDao<ErpOrderSplitGood> {

    public List<ErpOrderSplitGood> findBySplitId(@Param("originalSplitId") String splitId);
}
