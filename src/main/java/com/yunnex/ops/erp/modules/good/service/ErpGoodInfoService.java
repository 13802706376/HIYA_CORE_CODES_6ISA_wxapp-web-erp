package com.yunnex.ops.erp.modules.good.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.good.dao.ErpGoodInfoDao;
import com.yunnex.ops.erp.modules.good.entity.ErpGoodInfo;

/**
 * 商品信息管理Service
 * 
 * @author Frank
 * @version 2017-10-21
 */
@Service
@Transactional(readOnly = true)
public class ErpGoodInfoService extends CrudService<ErpGoodInfoDao, ErpGoodInfo> {

    @Autowired
    private ErpGoodInfoDao erpGoodInfoDao;

    @Override
    public ErpGoodInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpGoodInfo> findList(ErpGoodInfo erpGoodInfo) {
        return super.findList(erpGoodInfo);
    }

    @Override
    public Page<ErpGoodInfo> findPage(Page<ErpGoodInfo> page, ErpGoodInfo erpGoodInfo) {
        return super.findPage(page, erpGoodInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpGoodInfo erpGoodInfo) {
        super.save(erpGoodInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpGoodInfo erpGoodInfo) {
        super.delete(erpGoodInfo);
    }

    @Transactional(readOnly = false)
    public int updateCategoryId(ErpGoodInfo erpGoodInfo) {
        return erpGoodInfoDao.updateCategoryId(erpGoodInfo);
    }

    public ErpGoodInfo getDetail(String id) {
        return erpGoodInfoDao.getDetail(id);
    }

    @Transactional(readOnly = false)
    public boolean updateDetail(ErpGoodInfo erpGoodInfo) {
        return erpGoodInfoDao.updateDetail(erpGoodInfo) > 0;
    }

}
