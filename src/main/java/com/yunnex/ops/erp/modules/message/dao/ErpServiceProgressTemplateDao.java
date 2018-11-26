package com.yunnex.ops.erp.modules.message.dao;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceProgressTemplate;

/**
 * 服务进度模板表DAO接口
 * @author yunnex
 * @version 2018-07-04
 */
@MyBatisDao
public interface ErpServiceProgressTemplateDao extends CrudDao<ErpServiceProgressTemplate> {
	
}