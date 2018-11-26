package com.yunnex.ops.erp.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentQuestion;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitCommentQuestionDao;

/**
 * 分单评价问题Service
 * @author yunnex
 * @version 2018-04-04
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderSplitCommentQuestionService extends CrudService<ErpOrderSplitCommentQuestionDao, ErpOrderSplitCommentQuestion> {

	public ErpOrderSplitCommentQuestion get(String id) {
		return super.get(id);
	}
	
	public List<ErpOrderSplitCommentQuestion> findList(ErpOrderSplitCommentQuestion erpOrderSplitCommentQustion) {
		return super.findList(erpOrderSplitCommentQustion);
	}
	
	public Page<ErpOrderSplitCommentQuestion> findPage(Page<ErpOrderSplitCommentQuestion> page, ErpOrderSplitCommentQuestion erpOrderSplitCommentQustion) {
		return super.findPage(page, erpOrderSplitCommentQustion);
	}
	
	@Transactional(readOnly = false)
	public void save(ErpOrderSplitCommentQuestion erpOrderSplitCommentQustion) {
		super.save(erpOrderSplitCommentQustion);
	}
	
	@Transactional(readOnly = false)
	public void delete(ErpOrderSplitCommentQuestion erpOrderSplitCommentQustion) {
		super.delete(erpOrderSplitCommentQustion);
	}
	
}