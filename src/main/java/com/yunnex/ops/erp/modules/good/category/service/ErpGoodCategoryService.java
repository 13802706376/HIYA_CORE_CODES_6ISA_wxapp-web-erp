package com.yunnex.ops.erp.modules.good.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.good.category.dao.ErpGoodCategoryDao;
import com.yunnex.ops.erp.modules.good.category.entity.ErpGoodCategory;

/**
 * 商品分类管理Service
 * @author Frank
 * @version 2017-10-21
 */
@Service
@Transactional(readOnly = true)
public class ErpGoodCategoryService extends CrudService<ErpGoodCategoryDao, ErpGoodCategory> {
    
    @Autowired
    private ErpGoodCategoryDao erpGoodCategoryDao;

    @Override
    public ErpGoodCategory get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpGoodCategory> findList(ErpGoodCategory erpGoodCategory) {
		return super.findList(erpGoodCategory);
	}

    @Override
	public Page<ErpGoodCategory> findPage(Page<ErpGoodCategory> page, ErpGoodCategory erpGoodCategory) {
		return super.findPage(page, erpGoodCategory);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpGoodCategory erpGoodCategory) {
		super.save(erpGoodCategory);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpGoodCategory erpGoodCategory) {
		super.delete(erpGoodCategory);
	}
	
    public ErpGoodCategory getByGoodId(Long goodId) {
        return erpGoodCategoryDao.getByGoodId(goodId);
    }
}