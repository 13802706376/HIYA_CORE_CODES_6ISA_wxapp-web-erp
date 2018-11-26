package com.yunnex.ops.erp.modules.workflow.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.user.entity.ErpOrderFlowUser;

/**
 * 工作流人员关系DAO接口
 * @author Frank
 * @version 2017-10-27
 */
@MyBatisDao
public interface ErpOrderFlowUserDao extends CrudDao<ErpOrderFlowUser> {

    ErpOrderFlowUser findListByFlowId(@Param("procInsId") String procInsId,@Param("flowUserId") String flowUserId);

    List<ErpOrderFlowUser> findListByFlowIdAndUserId(@Param("procInsId")String procInsId,@Param("userId") String userId);
    
    ErpOrderFlowUser findByProcInsIdAndRoleName(@Param("procInsId") String procInsId, @Param("roleName") String roleName);
	
}