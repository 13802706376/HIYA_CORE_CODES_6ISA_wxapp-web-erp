package com.yunnex.ops.erp.modules.workflow.flow.common;


/**
 * 聚引客工作流常量定义信息表
 * 
 * @author yunnex
 * @date 2017年10月31日
 */
public class JykFlowConstants {
    
    /**策划专家接口人(流程启动处理用户)*/
    public static final String PLANNING_EXPERT_INTERFACE_MAN = "PlanningExpertInterfaceMan";
    /**策划专家*/
    public static final String Planning_Expert = "PlanningExpert";
    /**运营经理*/
    public static final String OPERATION_MANAGER="operationManager";
    /**运营顾问*/
    public static final String OPERATION_ADVISER="OperationAdviser";
    /**文案设计接口人*/
    public static final String assignTextDesignInterfacePerson="assignTextDesignInterfacePerson";
    /**指派投放顾问接口人*/
    public static final String assignConsultantInterface="assignConsultantInterface";
    
    /**文案设计*/
    public static final String assignTextDesignPerson="assignTextDesignPerson";
    /**设计师*/
    public static final String designer="designer";
    /**投放顾问*/
    public static final String assignConsultant="assignConsultant";
    
    public static final String promotionTimeDetermination ="promotion_time_determination";
    
    /**投放日期距离现在时间超于20个工作日的订单任务*/
    public static final int PLANNING_DATE_DISTINCT=20;
    
    
    
    /** 当前对象实例 */
    private static JykFlowConstants jykFlowConstants = new JykFlowConstants();
    /** 获取当前对象实例 */
    public static JykFlowConstants getInstance() {
        return jykFlowConstants;
    }



}
