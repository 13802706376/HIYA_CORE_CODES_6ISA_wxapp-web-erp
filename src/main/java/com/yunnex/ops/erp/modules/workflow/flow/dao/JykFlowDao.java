package com.yunnex.ops.erp.modules.workflow.flow.dao;

import java.util.List;
import java.util.Map;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.flow.entity.JykFlow;

/**
 * 开户流程信息表DAO接口
 * @author Frank
 * @version 2017-10-27
 */
@MyBatisDao
public interface JykFlowDao extends CrudDao<JykFlow> {
	
	JykFlow getByProcInstId(String procInsId);

	void updateFlowByProcIncId(JykFlow jykFlow);
	
	List<Map<String, Object>> findTaskAssignee(String procInsId);
	
}