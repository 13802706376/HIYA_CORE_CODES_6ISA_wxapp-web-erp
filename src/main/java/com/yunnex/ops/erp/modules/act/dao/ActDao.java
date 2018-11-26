/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yunnex.ops.erp.modules.act.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.act.entity.Act;
import com.yunnex.ops.erp.modules.workflow.flow.from.FlowForm;
import com.yunnex.ops.erp.modules.workflow.flow.from.WorkFlowQueryForm;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
	boolean deleteTask(String id);
	
	List<FlowForm> findTasks(WorkFlowQueryForm queryForm);
    
    int findTodoTaskCount(WorkFlowQueryForm queryForm);

    /**
     * 获取 流程id中流程节点是否完成（返回值大于0 ，则为完成）
     *
     * @param procInsId
     * @param taskDefKey
     * @return
     * @date 2018年7月9日
     * @author linqunzhi
     */
    public int getFinishTaskCount(@Param("procInsId") String procInsId, @Param("taskDefKey") String taskDefKey);

	
}
