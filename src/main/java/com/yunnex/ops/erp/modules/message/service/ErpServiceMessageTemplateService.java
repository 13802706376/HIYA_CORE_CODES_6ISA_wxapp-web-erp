package com.yunnex.ops.erp.modules.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceMessageTemplate;
import com.yunnex.ops.erp.modules.message.dao.ErpServiceMessageTemplateDao;

/**
 * 服务通知模板表Service
 * @author yunnex
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class ErpServiceMessageTemplateService extends CrudService<ErpServiceMessageTemplateDao, ErpServiceMessageTemplate> {

    @Override
	public ErpServiceMessageTemplate get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpServiceMessageTemplate> findList(ErpServiceMessageTemplate erpServiceMessageTemplate) {
		return super.findList(erpServiceMessageTemplate);
	}

    @Override
	public Page<ErpServiceMessageTemplate> findPage(Page<ErpServiceMessageTemplate> page, ErpServiceMessageTemplate erpServiceMessageTemplate) {
		return super.findPage(page, erpServiceMessageTemplate);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpServiceMessageTemplate erpServiceMessageTemplate) {
		super.save(erpServiceMessageTemplate);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpServiceMessageTemplate erpServiceMessageTemplate) {
		super.delete(erpServiceMessageTemplate);
	}
	
}