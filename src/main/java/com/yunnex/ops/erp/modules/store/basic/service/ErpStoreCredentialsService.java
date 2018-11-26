package com.yunnex.ops.erp.modules.store.basic.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStoreCredentialsDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreCredentials;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 商户营业资质信息Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStoreCredentialsService extends CrudService<ErpStoreCredentialsDao, ErpStoreCredentials> {

    @Autowired
    private ErpStoreInfoService erpStoreInfoService;

    @Override
	public ErpStoreCredentials get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpStoreCredentials> findList(ErpStoreCredentials erpStoreCredentials) {
		return super.findList(erpStoreCredentials);
	}

    @Override
	public Page<ErpStoreCredentials> findPage(Page<ErpStoreCredentials> page, ErpStoreCredentials erpStoreCredentials) {
		return super.findPage(page, erpStoreCredentials);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpStoreCredentials erpStoreCredentials) {
		super.save(erpStoreCredentials);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpStoreCredentials erpStoreCredentials) {
		super.delete(erpStoreCredentials);
	}

    /**
     * 保存门店资质信息
     * 
     * @date 2018年4月4日
     */
    @Transactional(readOnly = false)
    public String saveCredentials(ErpStoreCredentials erpStoreCredentials) {
        // 检查是否可编辑
        if (erpStoreCredentials != null && StringUtils.isNotBlank(erpStoreCredentials.getErpStoreInfoId())) {
            ErpStoreInfo erpStoreInfo = this.erpStoreInfoService.get(erpStoreCredentials.getErpStoreInfoId());
            StoreUtils.checkEditable(erpStoreInfo.getAuditStatus());
            super.save(erpStoreCredentials);
            if (!StringUtils.equals(erpStoreInfo.getCredentialsId(), erpStoreCredentials.getId())) {
                erpStoreInfo.setCredentialsId(erpStoreCredentials.getId());
                this.erpStoreInfoService.save(erpStoreInfo);// 更新门店数据
            }
            return erpStoreCredentials.getId();// 返回资质ID
        } else {
            throw new RuntimeException("参数错误！");
        }
    }
	
}