package com.yunnex.ops.erp.modules.visit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.visit.dao.ErpVisitServiceProductRecordDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceProductRecord;

/**
 * 交付产品选择记录Service
 * @author yunnex
 * @version 2018-07-16
 */
@Service
@Transactional(readOnly = true)
public class ErpVisitServiceProductRecordService extends CrudService<ErpVisitServiceProductRecordDao, ErpVisitServiceProductRecord> {

    @Override
	public ErpVisitServiceProductRecord get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpVisitServiceProductRecord> findList(ErpVisitServiceProductRecord erpVisitServiceProductRecord) {
		return super.findList(erpVisitServiceProductRecord);
	}

    @Override
	public Page<ErpVisitServiceProductRecord> findPage(Page<ErpVisitServiceProductRecord> page, ErpVisitServiceProductRecord erpVisitServiceProductRecord) {
		return super.findPage(page, erpVisitServiceProductRecord);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpVisitServiceProductRecord erpVisitServiceProductRecord) {
		super.save(erpVisitServiceProductRecord);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpVisitServiceProductRecord erpVisitServiceProductRecord) {
		super.delete(erpVisitServiceProductRecord);
	}
}