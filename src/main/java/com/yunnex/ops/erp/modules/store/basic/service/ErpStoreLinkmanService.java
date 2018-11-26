package com.yunnex.ops.erp.modules.store.basic.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreLinkmanDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreLinkman;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 门店联系人信息Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreLinkmanService extends CrudService<ErpStoreLinkmanDao, ErpStoreLinkman> {

    @Autowired(required = true)
    private ErpStoreInfoService erpStoreInfoService;

    @Override
	public ErpStoreLinkman get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreLinkman> findList(ErpStoreLinkman erpStoreLinkman) {
		return super.findList(erpStoreLinkman);
	}

    @Override
	public Page<ErpStoreLinkman> findPage(Page<ErpStoreLinkman> page, ErpStoreLinkman erpStoreLinkman) {
		return super.findPage(page, erpStoreLinkman);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreLinkman erpStoreLinkman) {
		super.save(erpStoreLinkman);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreLinkman erpStoreLinkman) {
		super.delete(erpStoreLinkman);
	}

    /**
     * 保存门店联系人信息
     * 
     * @date 2018年4月4日
     */
    @Transactional(readOnly = false)
    public String saveLinkman(ErpStoreLinkman erpStoreLinkman) {
        // 检查是否可编辑
        if (erpStoreLinkman != null && StringUtils.isNotBlank(erpStoreLinkman.getErpStoreInfoId())) {
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoService.get(erpStoreLinkman.getErpStoreInfoId());
            StoreUtils.checkEditable(erpStoreInfo.getAuditStatus());
            super.save(erpStoreLinkman);
            return erpStoreLinkman.getId();// 返回联系人信息ID
        } else {
            throw new RuntimeException("参数错误！");
        }
    }
	
}