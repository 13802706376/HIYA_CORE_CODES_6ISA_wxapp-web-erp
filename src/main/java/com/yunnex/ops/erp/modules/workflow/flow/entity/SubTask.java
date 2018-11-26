package com.yunnex.ops.erp.modules.workflow.flow.entity;

public class SubTask {
   
    private int subTaskConsumTime;
    
    private String subTaskDetail;

    public int getSubTaskConsumTime() {
        return subTaskConsumTime;
    }

    public void setSubTaskConsumTime(int subTaskConsumTime) {
        this.subTaskConsumTime = subTaskConsumTime;
    }

    public String getSubTaskDetail() {
        return subTaskDetail;
    }

    public void setSubTaskDetail(String subTaskDetail) {
        this.subTaskDetail = subTaskDetail;
    }

    public SubTask(int subTaskConsumTime, String subTaskDetail) {
        super();
        this.subTaskConsumTime = subTaskConsumTime;
        this.subTaskDetail = subTaskDetail;
    }
    
    
    
    
}

