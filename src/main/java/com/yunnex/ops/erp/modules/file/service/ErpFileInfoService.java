package com.yunnex.ops.erp.modules.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.file.dao.ErpFileInfoDao;
import com.yunnex.ops.erp.modules.file.entity.ErpFileInfo;

/**
 * 文件信息Service
 * @author yunnex
 * @version 2017-12-16
 */
@Service
@Transactional(readOnly = true)
public class ErpFileInfoService extends CrudService<ErpFileInfoDao, ErpFileInfo> {

    @Override
	public ErpFileInfo get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpFileInfo> findList(ErpFileInfo erpFileInfo) {
		return super.findList(erpFileInfo);
	}

    @Override
	public Page<ErpFileInfo> findPage(Page<ErpFileInfo> page, ErpFileInfo erpFileInfo) {
		return super.findPage(page, erpFileInfo);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpFileInfo erpFileInfo) {
		super.save(erpFileInfo);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpFileInfo erpFileInfo) {
		super.delete(erpFileInfo);
	}
	
}