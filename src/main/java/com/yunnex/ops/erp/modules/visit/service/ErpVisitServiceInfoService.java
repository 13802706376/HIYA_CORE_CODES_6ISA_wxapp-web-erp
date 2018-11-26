package com.yunnex.ops.erp.modules.visit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.visit.dao.ErpVisitServiceInfoDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceInfo;

/**
 * 上门服务主数据Service
 * @author yunnex
 * @version 2018-07-16
 */
@Service
@Transactional(readOnly = true)
public class ErpVisitServiceInfoService extends CrudService<ErpVisitServiceInfoDao, ErpVisitServiceInfo> {

    @Override
	public ErpVisitServiceInfo get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpVisitServiceInfo> findList(ErpVisitServiceInfo erpVisitServiceInfo) {
		return super.findList(erpVisitServiceInfo);
	}

    @Override
	public Page<ErpVisitServiceInfo> findPage(Page<ErpVisitServiceInfo> page, ErpVisitServiceInfo erpVisitServiceInfo) {
		return super.findPage(page, erpVisitServiceInfo);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpVisitServiceInfo erpVisitServiceInfo) {
		super.save(erpVisitServiceInfo);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpVisitServiceInfo erpVisitServiceInfo) {
		super.delete(erpVisitServiceInfo);
	}

    /**
     * 获取上门信息
     *
     * @param procInsId
     * @param serviceGoalCode
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    public ErpVisitServiceInfo getByGoalCode(String procInsId, String serviceGoalCode) {
        ErpVisitServiceInfo info = dao.getByGoalCode(procInsId, serviceGoalCode);
        return info;
    }
	
}