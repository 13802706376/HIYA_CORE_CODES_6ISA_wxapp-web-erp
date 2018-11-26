package com.yunnex.ops.erp.modules.workflow.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.sys.entity.User;
import com.yunnex.ops.erp.modules.workflow.user.dao.ErpOrderFlowUserDao;
import com.yunnex.ops.erp.modules.workflow.user.entity.ErpOrderFlowUser;

/**
 * 工作流人员关系Service
 * 
 * @author Frank
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderFlowUserService extends CrudService<ErpOrderFlowUserDao, ErpOrderFlowUser> {
    
    @Override
    public ErpOrderFlowUser get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpOrderFlowUser> findList(ErpOrderFlowUser erpOrderFlowUser) {
        return super.findList(erpOrderFlowUser);
    }

    @Override
    public Page<ErpOrderFlowUser> findPage(Page<ErpOrderFlowUser> page, ErpOrderFlowUser erpOrderFlowUser) {
        return super.findPage(page, erpOrderFlowUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpOrderFlowUser erpOrderFlowUser) {
        super.save(erpOrderFlowUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpOrderFlowUser erpOrderFlowUser) {
        super.delete(erpOrderFlowUser);
    }
    @Transactional(readOnly = false)
    public void insertOrderFlowUser(String userId, String orderId, String splitId, String flowUserType, String flowId) {
        // 插入订单流程信息表
        ErpOrderFlowUser orderFlowUser = new ErpOrderFlowUser();
        ErpOrderFlowUser erpOrderFlowUser = dao.findByProcInsIdAndRoleName(flowId, flowUserType);
        orderFlowUser.setId(erpOrderFlowUser == null ? null : erpOrderFlowUser.getId());
        orderFlowUser.setOrderId(orderId);
        orderFlowUser.setSplitId(splitId);
        orderFlowUser.setFlowId(flowId);
        orderFlowUser.setUser(new User(userId));
        orderFlowUser.setFlowUserId(flowUserType);
        this.save(orderFlowUser);
        logger.info("插入订单关联用户角色表成功|对象:{}", orderFlowUser);
    }
    public ErpOrderFlowUser findListByFlowId(String procInsId,String flowUserId) {
        return dao.findListByFlowId(procInsId,flowUserId);
    }
    public List<ErpOrderFlowUser> findListByFlowIdAndUserId(String procInsId,String userId) {
        return dao.findListByFlowIdAndUserId(procInsId,userId);
    }
    
    public ErpOrderFlowUser findByProcInsIdAndRoleName(String procInsId, String roleName) {
        return dao.findByProcInsIdAndRoleName(procInsId, roleName);
    }
    
}
