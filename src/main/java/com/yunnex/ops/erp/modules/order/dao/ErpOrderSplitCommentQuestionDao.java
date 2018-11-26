package com.yunnex.ops.erp.modules.order.dao;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentQuestion;

/**
 * 分单评价问题DAO接口
 * @author yunnex
 * @version 2018-04-04
 */
@MyBatisDao
public interface ErpOrderSplitCommentQuestionDao extends CrudDao<ErpOrderSplitCommentQuestion> {
	
}