package com.yunnex.ops.erp.modules.workflow.flow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.workflow.flow.common.JykFlowConstants;
import com.yunnex.ops.erp.modules.workflow.flow.dao.JykFlowDao;
import com.yunnex.ops.erp.modules.workflow.flow.entity.JykFlow;
import com.yunnex.ops.erp.modules.workflow.user.service.ErpOrderFlowUserService;

/**
 * 聚引客流程处理信息表
 * 
 * @author yunnex
 * @date 2017年10月31日
 */
@Service
@Transactional(readOnly = true)
public class JykFlowService extends CrudService<JykFlowDao, JykFlow> {
    @Autowired
    private JykFlowDao jykFlowDao;
    /** 流程关联用户处理服务 */
    @Autowired
    private ErpOrderFlowUserService erpOrderFlowUserService;

    /**
     * 获取聚引客业务表信息
     * 
     * @param procInstId
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    public JykFlow getByProcInstId(String procInstId) {
        return jykFlowDao.getByProcInstId(procInstId);
    }

    /**
     * 聚引客流程启动
     */
    @Override
    @Transactional(readOnly = false)
    public void save(JykFlow jykFlow) {
        jykFlow.preInsert();
        dao.insert(jykFlow);
        String flowUserType = JykFlowConstants.PLANNING_EXPERT_INTERFACE_MAN;
        // 插入流程关联用户信息表
        erpOrderFlowUserService.insertOrderFlowUser(jykFlow.getPlanningExpertInterface(), jykFlow.getOrderId(), jykFlow.getSplitId(), flowUserType,
                        jykFlow.getProcInsId());
    }

    


}
