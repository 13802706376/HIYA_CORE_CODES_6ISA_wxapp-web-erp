package com.yunnex.ops.erp.modules.schedule.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.schedule.dao.ErpHisSplitDao;
import com.yunnex.ops.erp.modules.schedule.entity.ErpHisSplit;

/**
 * 生产进度小程序父表Service
 * @author pengchenghe
 * @version 2018-01-19
 */
@Service
@Transactional(readOnly = true)
public class ErpHisSplitService extends CrudService<ErpHisSplitDao, ErpHisSplit> {

    @Override
	public ErpHisSplit get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpHisSplit> findList(ErpHisSplit erpHisSplit) {
		return super.findList(erpHisSplit);
	}

    @Override
	public Page<ErpHisSplit> findPage(Page<ErpHisSplit> page, ErpHisSplit erpHisSplit) {
		return super.findPage(page, erpHisSplit);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpHisSplit erpHisSplit) {
		super.save(erpHisSplit);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpHisSplit erpHisSplit) {
		super.delete(erpHisSplit);
	}
	
	public List<ErpHisSplit> findAllListWhereSplitId(@Param("splitId")String splitId){
		return dao.findAllListWhereSplitId(splitId);
	}
	
}