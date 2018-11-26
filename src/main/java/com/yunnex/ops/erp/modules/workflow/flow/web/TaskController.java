package com.yunnex.ops.erp.modules.workflow.flow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.workflow.flow.task.WorkFlowTaskScheduler;

@Controller
@RequestMapping(value = "/task")
public class TaskController extends BaseController {

    @Autowired
    private WorkFlowTaskScheduler jykFlowTask;
    
    @RequestMapping("changeState")
    public @ResponseBody Boolean change() {
        jykFlowTask.changeState();
        return true;
    }

}
