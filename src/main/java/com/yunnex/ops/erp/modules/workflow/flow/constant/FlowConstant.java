package com.yunnex.ops.erp.modules.workflow.flow.constant;

public interface FlowConstant {
    /**
     * 暂停原因字典
     */
    String SUSPEND_REASON = "suspend_reason";

    String MESSAGE = "message";
    String RESULT = "result";
    boolean RESULT_TRUE = true;
    boolean RESULT_FALSE = false;
    
    String STRING_0 = "0";
    String STRING_1 = "1";
    String STRING_2 = "2";

    /**文本类型：普通类型*/
    public static final String FLOW_FORM_DATA_ATTR_TYPE_NORMAL = "NORMAL";
    /**文本类型：富文本类型*/
    public static final String FLOW_FORM_DATA_ATTR_TYPE_TEXT = "TEXT";
    /**勾选框，勾选*/
    public static final  String CHECKBOX_IS_YES = "Y";
    
    //public static final  String   NO = "N";

    // 预览图片标识
    String CHANNEL_OUTERIMGFRIENDS = "outerImgFriends"; // 朋友圈外层入口截图
    String CHANNEL_INNERIMGFRIENDS = "innerImgFriends"; // 朋友圈落地页截图
    String CHANNEL_OUTERIMGWEIBO = "outerImgWeibo"; // 微博外层入口截图
    String CHANNEL_INNERIMGWEIBO = "innerImgWeibo"; // 微博落地页截图
    String CHANNEL_OUTERIMGMOMO = "outerImgMomo"; // 陌陌外层入口截图
    String CHANNEL_INNERIMGMOMO = "innerImgMomo"; // 陌陌落地页截图

    // 推广上线监测
    public static final String PROMOTE_ONLINE_MONITOR_MOMO = "promote_online_monitor_momo";
    public static final String PROMOTE_ONLINE_MONITOR_MICROBLOG = "promote_online_monitor_microblog";
    public static final String PROMOTE_ONLINE_MONITOR_FRIENDS = "promote_online_monitor_friends";
    // 推广上线
    public static final String PROMOTE_ONLINE_MOMO = "promote_online_momo";
    public static final String PROMOTE_ONLINE_MICROBLOG = "promote_online_microblog";
    public static final String PROMOTE_ONLINE_FRIENDS = "promote_online_friends";

    // 推广提案提审
    public static final String PROMOTION_PROPOSAL_AUDIT = "promotion_proposal_audit_";
    // 修改推广提案
    public static final String MODIFY_PROMOTION_PROPOSAL = "modify_promotion_proposal";
    // 指派策划专家
    public static final String ASSIGNE_PLANNING_EXPERT = "assigne_planning_expert";
    // 经营诊断
    public static final String MANAGEMENT_DIAGNOSIS_MARKETING_PLANNING = "management_diagnosis_marketing_planning_";
    // 推广方案预览确认
    public static final String PROMOTION_PLAN_PREVIEW_CONFIRMATION = "promotion_plan_preview_confirmation_";
    // 输出推广页面预览给策划专家
    public static final String WORK_PROMOTION_SCHEME_PREVIEW = "work_promotion_scheme_preview_";
    // 输出设计稿
    public static final String OUTPUT_DESIGN_DRAFT = "output_design_draft_";
    // 输出文案
    public static final String OUTPUT_OFFICIAL_DOCUMENTS = "output_official_documents_";
    // 修改设计稿
    public static final String MODIFY_DESIGN_DRAFT = "modify_design_draft_";
    // 修改文案
    public static final String MODIFY_OFFICIAL_DOCUMENTS = "modify_official_documents_";
    // 审核设计稿
    public static final String REVIEW_DESIGN_DRAFT = "review_design_draft_";
    // 审核文案
    public static final String REVIEW_OFFICIAL_DOCUMENTS = "review_official_documents_";

    /** 首次审核订单 */
    public static final String ORDER_REVIEW_FIRST = "order_review_first";
    /** 二次订单审核 */
    public static final String ORDER_REVIEW_SECOND = "order_review_second";
    /** 修改/删除订单 */
    public static final String MODIFY_ORDER_INFO = "modify_order_info";

    /**等待推广状态同步*/
    public final String WAITING_PROMOTION_STATE_SYNC = "waiting_promotion_state_sync_3.2";

    /**微博充值审核结果*/
    public static final String  MICROBLOG_RECHARGE_REVIEW_RESULT = "microblogRechargeReviewResult";
    /**是否确认微博推广充值 */
    public static final String CHOOSE_MICROBLOG_RECHARGE_FLAG =  "chooseMicroblogRechargeFlag";
    
    /**确认投放信息*/
    public static final String PUTINFO_CHECKBOX = "putInfoCheckBox";
    /**确认最终推广上线时间*/
    public static final String PROMOTION_TIME_CHECKBOX = "promotionTimeCheckBox";
    /** 确认广告主开户成功通知商户*/
    public static final String NOTIFY_SHOP_CHECKBOX = "notifyShopCheckBox";
    /** 确认 首日投放数据简报给通知商户*/
    public static final String FIRST_DAY_PROMOTE_DATA_INFORM_SHOP="firstDayPromoteDataInformShop";
    /** 推广状态同步*/
    public static final String INFORM_SHOP_UP_LINE= "InformShopUpLine";
    /** 讲效果报告上传至OEM后台，并修改推广状态*/
    public static final String  EFFECT_REPORT_UPLOAD_OEM= "effectReportuploadOEM";
    /**效果报告通知商户查看 */
    public static final String  EFFECT_REPORT_INFORM_SHOP_CHECK= "effectReportInformShopCheck";
    /**是否完成卡券信息输入 */
    public static final String  IS_CARD_INFO_INPUT="isCardInfoInput";
    /**确认是否进行微博充值*/
    public static final String IS_SURE_RECHARGE = "isSureRecharge";
    
    
    /**微博充值审核 */
    public static final String MICROBLO_RECHARGE_CHECK = "microblogRechargeCheck";
    
    public static final String MICROBLO_RECHARGE_CHECK_FAIL_REASON =  "microblogRechargeCheckFailReason";
    
    /**朋友圈开户审核 */
    public static final String FRIENDS_PROMOTION_FLOW_CHECK_V1 = "friendsPromotionFlowCheckV1";
    public static final String FRIENDS_PROMOTION_FLOW_CHECK_FAIL_REASON_V1 =  "friendsPromotionFlowCheckFailReasonV1";
    
    /**微博开户审核 */
    public static final String MICROBLOG_PROMOTION_FLOW_CHECK_V1 = "microblogPromotionFlowCheckV1";
    public static final String MICROBLOG_PROMOTION_FLOW_CHECK_FAIL_REASON_V1 =  "microblogPromotionFlowCheckFailReasonV1";
    
    /** 开通服务 **/
    public static final String ACCOUNT_PAY_OPEN = "account_pay_open";
    /** 营销策划 **/
    public static final String MARKETING_PLANNING = "marketing_planning";
    /** 服务启动 **/
    public static final String SERVICE_STARTUP = "service_startup";
    /** 物料服务 **/
    public static final String MATERIAL_SERVICE = "material_service";
    /** 服务商类型 **/
    public static final String AGENTTYPE = "agentType";

    public static final String SERVICETYPE = "serviceType";

    public static final String FINISH = "finish";
    public static final String RUNNING = "running";
    
    public  static  final String  SERVICE_SOURCE_ID="service_source_id";
    public  static  final String  HAS_START_FLOW="has_start_flow";
    
    /** 公众号开通 */
    String PUBLIC_NUMBER_OPEN = "public_number_open";

    // ================================流程角色
    /** 订单初审员 **/
    public static final String FIRST_ORDER_AUDITOR_FLOWROLE = "firstOrderAuditor";
    /** 订单创建人 **/
    public static final String ORDER_CREATOR_FLOWROLE = "orderCreator";
    /** 二次审核员 **/
    public static final String SECOND_ORDER_AUDITOR_FLOWROLE = "secondOrderAuditor";
    // ================================系统角色
    /** 订单初审员 **/
    public static final String FIRST_ORDER_AUDITOR_SYSROLE = "first_order_auditor";
    /** 二次审核员 **/
    public static final String SECOND_ORDER_AUDITOR_SYSROLE = "second_order_auditor";
    /** 订单创建人 **/
    public static final String ORDER_CREATOR_SYSROLE = "order_creator";

    /**
     * 服务流程类型
     * 
     * @author linqunzhi
     * @date 2018年7月10日
     */
    interface ServiceType {

        /** 引流推广服务 */
        String SPLIT_JU_YIN_KE = "SplitJuYinKe";

    }

    /**
     * 智能客流运营全套落地服务
     * 
     * @author linqunzhi
     * @date 2018年7月27日
     */
    interface NodeKeyFMPS {
        // ##### 3.3 #######
        /** 上门服务提醒(首次营销策划服务) */
        String VISIT_SERVICE_REMIND_FIRST_3_3 = "visit_service_remind_first_3.3";
        /** 上门服务提醒(物料实施服务) */
        String VISIT_SERVICE_REMIND_MATERIAL_3_3 = "visit_service_remind_material_3.3";
    }

    /**
     * 聚引客上门交付服务
     * 
     * @author linqunzhi
     * @date 2018年7月27日
     */
    interface NodeKeyJYK {
        // ##### 3.3 #######
        /** 上门服务提醒(聚引客上门交付服务) */
        String VISIT_SERVICE_REMIND_JYK_3_3 = "visit_service_remind_jyk_3.3";
    }
}
