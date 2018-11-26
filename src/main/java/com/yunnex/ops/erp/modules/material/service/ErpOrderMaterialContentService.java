package com.yunnex.ops.erp.modules.material.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.material.dao.ErpOrderMaterialContentDao;
import com.yunnex.ops.erp.modules.material.entity.ErpOrderMaterialContent;

/**
 * 订单物料内容Service
 * 
 * @author yunnex
 * @version 2018-07-13
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderMaterialContentService extends CrudService<ErpOrderMaterialContentDao, ErpOrderMaterialContent> {

    @Override
    public ErpOrderMaterialContent get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpOrderMaterialContent> findList(ErpOrderMaterialContent erpOrderMaterialContent) {
        return super.findList(erpOrderMaterialContent);
    }

    @Override
    public Page<ErpOrderMaterialContent> findPage(Page<ErpOrderMaterialContent> page, ErpOrderMaterialContent erpOrderMaterialContent) {
        return super.findPage(page, erpOrderMaterialContent);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpOrderMaterialContent erpOrderMaterialContent) {
        super.save(erpOrderMaterialContent);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpOrderMaterialContent erpOrderMaterialContent) {
        super.delete(erpOrderMaterialContent);
    }

    public List<ErpOrderMaterialContent> findByOrderNumber(String orderNumber, String delFlag) {
        return dao.findByOrderNumber(orderNumber, delFlag);
    }

    @Transactional
    public int deleteByOrderNumber(String orderNumber) {
        return dao.deleteByOrderNumber(orderNumber);
    }

    @Transactional
    public int saveBatch(List<ErpOrderMaterialContent> list) {
        return dao.saveBatch(list);
    }


}
