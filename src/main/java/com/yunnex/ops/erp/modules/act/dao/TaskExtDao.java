package com.yunnex.ops.erp.modules.act.dao;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.act.entity.TaskExt;

/**
 * act_ru_task_ext生成DAO接口
 * @author act_ru_task_ext生成
 * @version 2018-01-13
 */
@MyBatisDao
public interface TaskExtDao extends CrudDao<TaskExt> {
	
    void updateTaskState(TaskExt taskExt);
    
}