package com.yunnex.ops.erp.modules.visit.dao;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceProductRecord;

/**
 * 交付产品选择记录DAO接口
 * @author yunnex
 * @version 2018-07-16
 */
@MyBatisDao
public interface ErpVisitServiceProductRecordDao extends CrudDao<ErpVisitServiceProductRecord> {
	
}