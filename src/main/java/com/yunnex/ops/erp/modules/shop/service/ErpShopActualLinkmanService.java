package com.yunnex.ops.erp.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.shop.dao.ErpShopActualLinkmanDao;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopActualLinkman;

/**
 * 商户实际联系人信息Service
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpShopActualLinkmanService extends CrudService<ErpShopActualLinkmanDao, ErpShopActualLinkman> {

    @Override
	public ErpShopActualLinkman get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpShopActualLinkman> findList(ErpShopActualLinkman erpShopActualLinkman) {
		return super.findList(erpShopActualLinkman);
	}

    @Override
	public Page<ErpShopActualLinkman> findPage(Page<ErpShopActualLinkman> page, ErpShopActualLinkman erpShopActualLinkman) {
		return super.findPage(page, erpShopActualLinkman);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpShopActualLinkman erpShopActualLinkman) {
		super.save(erpShopActualLinkman);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpShopActualLinkman erpShopActualLinkman) {
		super.delete(erpShopActualLinkman);
	}
	
}