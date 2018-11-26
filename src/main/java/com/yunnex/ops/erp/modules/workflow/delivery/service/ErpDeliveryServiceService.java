package com.yunnex.ops.erp.modules.workflow.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.workflow.delivery.dao.ErpDeliveryServiceDao;
import com.yunnex.ops.erp.modules.workflow.delivery.entity.ErpDeliveryService;

/**
 * erp_delivery_serviceService
 * @author hanhan
 * @version 2018-05-26
 */
@Service
@Transactional(readOnly = true)
public class ErpDeliveryServiceService extends CrudService<ErpDeliveryServiceDao, ErpDeliveryService> {
    @Autowired
    private ErpDeliveryServiceDao erpDeliveryServiceDao;
	public ErpDeliveryService get(String id) {
		return super.get(id);
	}
	
	public List<ErpDeliveryService> findList(ErpDeliveryService erpDeliveryService) {
		return super.findList(erpDeliveryService);
	}
	
	public Page<ErpDeliveryService> findPage(Page<ErpDeliveryService> page, ErpDeliveryService erpDeliveryService) {
		return super.findPage(page, erpDeliveryService);
	}
	
	@Transactional(readOnly = false)
	public void save(ErpDeliveryService erpDeliveryService) {
		super.save(erpDeliveryService);
	}
	
	@Transactional(readOnly = false)
	public void delete(ErpDeliveryService erpDeliveryService) {
		super.delete(erpDeliveryService);
	}
	
	@Transactional(readOnly = false)
    public ErpDeliveryService getDeliveryInfoByProsIncId(String procInsId) {
	   return erpDeliveryServiceDao.getDeliveryInfoByProsIncId(procInsId);
    }
	@Transactional(readOnly = false)
    public  List<String>  findTaskIdByShopId(String zhangbeiId) {
       return erpDeliveryServiceDao.findTaskIdByShopId(zhangbeiId);
    }
    public ErpDeliveryService getDeliveryInfoByOrederId(String orderId) {
        return erpDeliveryServiceDao.getDeliveryInfoByOrederId(orderId);
    }
    
    /**
     * 正在跑的流程 ，根据流程类型集合 和 角色类型集合 获取人员id
     * 
     * @param zhangbeiId
     * @param typeList
     * @param roleTypeList
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    public List<String> findBeginByTypeAndRole(String zhangbeiId, List<String> typeList, List<String> roleTypeList) {
        logger.info("findBeginByTypeAndRole start");
        String typeListStr = JSON.toJSONString(typeList);
        String roleTypeListStr = JSON.toJSONString(roleTypeList);
        logger.info("param :zhangbeiId={}|typeList={}|roleTypeList", zhangbeiId, typeListStr, roleTypeListStr);
        List<String> result = erpDeliveryServiceDao.findBeginByTypeAndRole(zhangbeiId, typeList, roleTypeList);
        logger.info("findBeginByTypeAndRole end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据掌贝id 和 结束状态 获取列表
     *
     * @param zhangbeiId
     * @param endFlag
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     * @param serviceTypeList
     */
    public List<ErpDeliveryService> findByEndFlag(String zhangbeiId, String endFlag, List<String> serviceTypeList) {
        List<ErpDeliveryService> result = dao.findByEndFlag(zhangbeiId, endFlag, serviceTypeList);
        return result;
    }

    /**
     * 获取智慧餐厅服务 列表
     *
     * @param zhangbeiId
     * @param endFlag
     * @return
     * @date 2018年9月3日
     * @author linqunzhi
     */
    public List<ErpDeliveryService> findZhiHuiByEndFlag(String zhangbeiId, String endFlag) {
        logger.info("findZhiHuiByEndFlag start | zhangbeiId={}|endFlag={}", zhangbeiId, endFlag);
        List<ErpDeliveryService> result = dao.findZhiHuiByEndFlag(zhangbeiId, endFlag);
        logger.info("findZhiHuiByEndFlag end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

}