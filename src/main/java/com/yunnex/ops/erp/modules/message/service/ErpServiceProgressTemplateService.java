package com.yunnex.ops.erp.modules.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceProgressTemplate;
import com.yunnex.ops.erp.modules.message.dao.ErpServiceProgressTemplateDao;

/**
 * 服务进度模板表Service
 * @author yunnex
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class ErpServiceProgressTemplateService extends CrudService<ErpServiceProgressTemplateDao, ErpServiceProgressTemplate> {

    @Override
	public ErpServiceProgressTemplate get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpServiceProgressTemplate> findList(ErpServiceProgressTemplate erpServiceScheduleTemplate) {
		return super.findList(erpServiceScheduleTemplate);
	}

    @Override
	public Page<ErpServiceProgressTemplate> findPage(Page<ErpServiceProgressTemplate> page, ErpServiceProgressTemplate erpServiceScheduleTemplate) {
		return super.findPage(page, erpServiceScheduleTemplate);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpServiceProgressTemplate erpServiceScheduleTemplate) {
		super.save(erpServiceScheduleTemplate);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpServiceProgressTemplate erpServiceScheduleTemplate) {
		super.delete(erpServiceScheduleTemplate);
	}
	
}