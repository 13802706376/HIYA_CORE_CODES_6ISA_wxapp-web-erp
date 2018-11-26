package com.yunnex.ops.erp.modules.store.basic.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreLegalPersonDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreLegalPerson;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 法人信息Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreLegalPersonService extends CrudService<ErpStoreLegalPersonDao, ErpStoreLegalPerson> {

    @Autowired
    private ErpStoreInfoService erpStoreInfoService;

    @Override
	public ErpStoreLegalPerson get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreLegalPerson> findList(ErpStoreLegalPerson erpStoreLegalPerson) {
		return super.findList(erpStoreLegalPerson);
	}

    @Override
	public Page<ErpStoreLegalPerson> findPage(Page<ErpStoreLegalPerson> page, ErpStoreLegalPerson erpStoreLegalPerson) {
		return super.findPage(page, erpStoreLegalPerson);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreLegalPerson erpStoreLegalPerson) {
		super.save(erpStoreLegalPerson);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreLegalPerson erpStoreLegalPerson) {
		super.delete(erpStoreLegalPerson);
	}

    /**
     * 保存门店法人信息
     * 
     * @date 2018年4月4日
     */
    @Transactional(readOnly = false)
    public String saveLegalPerson(ErpStoreLegalPerson erpStoreLegalPerson) {
        // 检查是否可编辑
        if (erpStoreLegalPerson != null && StringUtils.isNotBlank(erpStoreLegalPerson.getErpStoreInfoId())) {
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoService.get(erpStoreLegalPerson.getErpStoreInfoId());
            StoreUtils.checkEditable(erpStoreInfo.getAuditStatus());
            super.save(erpStoreLegalPerson);
            if (!StringUtils.equals(erpStoreInfo.getLegalPersonId(), erpStoreLegalPerson.getId())) {
                erpStoreInfo.setLegalPersonId(erpStoreLegalPerson.getId());
                this.erpStoreInfoService.save(erpStoreInfo);// 更新门店数据
            }
            return erpStoreLegalPerson.getId();// 返回法人信息ID
        } else {
            throw new RuntimeException("参数错误！");
        }
    }

}