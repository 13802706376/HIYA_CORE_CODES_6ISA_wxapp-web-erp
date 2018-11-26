package com.yunnex.ops.erp.modules.qualify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.qualify.dao.ErpShopExtensionQualifyDao;
import com.yunnex.ops.erp.modules.qualify.entity.ErpShopExtensionQualify;

/**
 * 商户推广资质Service
 * @author huanghaidong
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class ErpShopExtensionQualifyService extends CrudService<ErpShopExtensionQualifyDao, ErpShopExtensionQualify> {

    @Autowired
    private ErpShopExtensionQualifyDao erpShopExtensionQualifyDao;

    @Override
	public ErpShopExtensionQualify get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpShopExtensionQualify> findList(ErpShopExtensionQualify erpShopExtensionQualify) {
		return super.findList(erpShopExtensionQualify);
	}

    @Override
	public Page<ErpShopExtensionQualify> findPage(Page<ErpShopExtensionQualify> page, ErpShopExtensionQualify erpShopExtensionQualify) {
		return super.findPage(page, erpShopExtensionQualify);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpShopExtensionQualify erpShopExtensionQualify) {
		super.save(erpShopExtensionQualify);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpShopExtensionQualify erpShopExtensionQualify) {
		super.delete(erpShopExtensionQualify);
	}

    public List<String> findExtensionQualifyList(String shopId) {
        return erpShopExtensionQualifyDao.findExtensionQualifyList(shopId);
    }
	
}