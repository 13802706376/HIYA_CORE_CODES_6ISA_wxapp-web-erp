package com.yunnex.ops.erp.modules.visit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.visit.dao.ErpVisitServiceItemDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceItem;

/**
 * 上门服务项目类型Service
 * @author yunnex
 * @version 2018-07-16
 */
@Service
@Transactional(readOnly = true)
public class ErpVisitServiceItemService extends CrudService<ErpVisitServiceItemDao, ErpVisitServiceItem> {

    @Override
	public ErpVisitServiceItem get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpVisitServiceItem> findList(ErpVisitServiceItem erpVisitServiceItem) {
		return super.findList(erpVisitServiceItem);
	}

    @Override
	public Page<ErpVisitServiceItem> findPage(Page<ErpVisitServiceItem> page, ErpVisitServiceItem erpVisitServiceItem) {
		return super.findPage(page, erpVisitServiceItem);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpVisitServiceItem erpVisitServiceItem) {
		super.save(erpVisitServiceItem);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpVisitServiceItem erpVisitServiceItem) {
		super.delete(erpVisitServiceItem);
	}

    /**
     * 根据visitInfoId 获取服务项目
     *
     * @param visitInfoId
     * @return
     * @date 2018年8月7日
     * @author linqunzhi
     */
    public List<ErpVisitServiceItem> findByVisitInfoId(String visitInfoId) {
        logger.info("findByVisitInfoId start | visitServiceInfoId={}", visitInfoId);
        List<ErpVisitServiceItem> result = dao.findByVisitInfoId(visitInfoId);
        logger.info("findByVisitInfoId end | size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据visitInfoId 获取上门目的名称
     *
     * @param visitServiceInfoId
     * @return
     * @date 2018年8月9日
     * @author linqunzhi
     */
    public String getServiceGoalName(String visitServiceInfoId) {
        logger.info("getServiceGoalName start | visitServiceInfoId={}", visitServiceInfoId);
        String result = dao.getServiceGoalName(visitServiceInfoId);
        logger.info("getServiceGoalName end | result = {}", result);
        return result;
    }
	
}