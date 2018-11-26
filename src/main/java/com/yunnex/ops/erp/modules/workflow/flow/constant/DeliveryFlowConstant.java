package com.yunnex.ops.erp.modules.workflow.flow.constant;

public interface DeliveryFlowConstant {

    //流程节点变量
    /** 查看订单信息,指派订单处理人员 **/
    public static final String ASSIGN_ORDER_HANDLERS = "assign_order_handlers";
    
    /** 电话联系商户,确认服务内容 **/
    public static final String TELEPHONE_CONFIRM_SERVICE = "telephone_confirm_service";
    
    /** 进件资料收集 **/
    public static final String INTO_MATERIAL_COLLECTION = "into_material_collection";
    
    /**掌贝门店创建 **/
    public static final String ZHANGBEI_STORE_CREATE = "zhangbei_store_create";
    
    /** 支付宝口碑申请**/
    public static final String ALIPAY_PUBLIC_PRAISE_APPLY = "alipay_public_praise_apply";
    
    /** 公众号开通**/
    public static final String PUBLIC_NUMBER_OPEN = "public_number_open";
    
    /** 微信账号开通 **/
    public static final String WECHAT_ACCOUNT_OPEN = "wechat_account_open";
    
    /** 微信支付商户号配置 **/
    public static final String WECHAT_SHOP_CONFIGURATION = "wechat_shop_configuration";
    
    /** 银联支付开通 **/
    public static final String UNIONPAY_ACCOUNT_OPEN = "unionpay_account_open";
    
    /** 银联支付培训测试（远程） **/
    public static final String UNIONPAY_ACCOUNT_TRAIN = "unionpay_account_train";
    /** 掌贝账号开通(提示) **/
    public static final String ZHANGBEI_ACCOUNT_OPEN = "zhangbei_account_open";
    
    /** 上门服务完成（首次营销策划服务）任务 完成节点 */
    String VISIT_SERVICE_COMPLETE_FIRST = "visit_service_complete_first";
    /** 上门服务完成（物料服务）任务 完成节点 */
    String VISIT_SERVICE_COMPLETE_MATERIAL = "visit_service_complete_material";
    /** 上门服务完成（JYK）任务 完成节点 */
    String VISIT_SERVICE_COMPLETE_JYK = "visit_service_complete_jyk";
    /** 制作物料内容提交 完成节点 */
    String MATERIAL_MAKE_SUBMIT = "material_make_submit";
    /** 商户资料收集 任务 完成节点 */
    String SHOP_INFO_COLLECTION = "shop_info_collection";
    /** 电话预约商户(聚引客) 完成节点 */
    String VISIT_SERVICE_SUBSCRIBE_JYK = "visit_service_subscribe_jyk";
    /**物料制作跟踪(物料更新服务) */
    String MATERIAL_MAKE_FOLLOW_UPDATE="material_make_follow_update";
    /**物料制作跟踪 */
    String MATERIAL_MAKE_FOLLOW="material_make_follow";
    
    /** 开通服务结束监听 */
    String OPEN_SERVICE_END_LISTENER = "open_service_end_listener";
    /** 上门服务提醒 */
    String VISIT_SERVICE_REMIND_JYK = "visit_service_remind_jyk";
    /** 上门服务提醒 */
    String VISIT_SERVICE_REMIND_FIRST = "visit_service_remind_first";
    /** 上门服务提醒 */
    String VISIT_SERVICE_REMIND_MATERIAL = "visit_service_remind_material";

    String VISIT_SERVICE_REMIND = "visit_service_remind";

    String  VISIT_SERVICE_REMIND_FIRST_3V3 = "visit_service_remind_first_3.3";
    //流程变量
    /** 有无 掌贝账号*/
    public static final String ZHANG_BEI_FLAG = "zhangbeiFlag";
    /** 有无口碑门店 */
    public static final String  PUBLIC_PRAISE_FLAG="publicPraiseFlag";
    /** 有无 微信公众号公众号*/
    public static final String PUBLIC_NUMBER_FLAG= "publicNumberFlag";
    /**有无 微信账号*/
    public static final String WECHAT_ACCOUNT_FLAG="wechatAccountFlag";
    /**有无 银联账号*/
    public static final String UNIONPAY_ACCOUNT_FLAG="unionPayAccountFlag";
    /**是否 开通银联支付*/
    public static final String UNIONPAY_OPEN_FLAG="unionpayOpenFlag";
    /** 服务类型 （聚引客 、 客常来） */
    String SERVICE_TYPE = "serviceType";
    
    // 流程变量值
    /** 客常来 */
    String SERVICE_TYPE_KE_CHANG_LAI = "FMPS";
    /** 聚引客交付服务 */
    String SERVICE_TYPE_JU_YIN_KE = "JYK";
    /** 掌贝平台交付服务*/
    String SERVICE_TYPE_JU_YIN_DATA = "INTO_PIECES";
    /** 首次上门服务（基础版） */
    String SERVICE_TYPE_KE_CHANG_LAI_BASIC = "FMPS_BASIC";
    /** 智慧餐厅首次服务 流程 */
    String SERVICE_TYPE_KE_WR = "WR";
    /** 物料跟新 */
    String SERVICE_TYPE_MU = "MU";
    /** 上门收费 */
    String SERVICE_TYPE_VC = "VC";
}
