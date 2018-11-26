package com.yunnex.ops.erp.modules.schedule.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.schedule.dao.ErpHisSplitValueDao;
import com.yunnex.ops.erp.modules.schedule.entity.ErpHisSplitValue;

/**
 * 生产进度小程序子表Service
 * @author pengchenghe
 * @version 2018-01-19
 */
@Service
@Transactional(readOnly = true)
public class ErpHisSplitValueService extends CrudService<ErpHisSplitValueDao, ErpHisSplitValue> {

    @Override
	public ErpHisSplitValue get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpHisSplitValue> findList(ErpHisSplitValue erpHisSplitValue) {
		return super.findList(erpHisSplitValue);
	}

    @Override
	public Page<ErpHisSplitValue> findPage(Page<ErpHisSplitValue> page, ErpHisSplitValue erpHisSplitValue) {
		return super.findPage(page, erpHisSplitValue);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpHisSplitValue erpHisSplitValue) {
		super.save(erpHisSplitValue);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpHisSplitValue erpHisSplitValue) {
		super.delete(erpHisSplitValue);
	}
	
	public List<ErpHisSplitValue> findAllListWhereHisId(String hisId){
		return dao.findAllListWhereHisId(hisId);
	}
	
}