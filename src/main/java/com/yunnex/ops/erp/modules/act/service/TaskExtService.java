package com.yunnex.ops.erp.modules.act.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.act.dao.TaskExtDao;
import com.yunnex.ops.erp.modules.act.entity.TaskExt;

/**
 * act_ru_task_ext生成Service
 * @author act_ru_task_ext生成
 * @version 2018-01-13
 */
@Service
@Transactional(readOnly = true)
public class TaskExtService extends CrudService<TaskExtDao, TaskExt> {

    @Override
	public TaskExt get(String id) {
		return super.get(id);
	}

    @Override
	public List<TaskExt> findList(TaskExt taskExt) {
		return super.findList(taskExt);
	}

    @Override
	public Page<TaskExt> findPage(Page<TaskExt> page, TaskExt taskExt) {
		return super.findPage(page, taskExt);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(TaskExt taskExt) {
		super.save(taskExt);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(TaskExt taskExt) {
		super.delete(taskExt);
	}
	
	@Transactional(readOnly = false)
	public void updateTaskState(TaskExt taskExt) {
	    dao.updateTaskState(taskExt);
	}
}