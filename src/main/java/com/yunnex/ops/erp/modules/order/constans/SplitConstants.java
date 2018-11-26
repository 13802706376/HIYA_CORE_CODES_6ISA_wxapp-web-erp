package com.yunnex.ops.erp.modules.order.constans;

/**
 * 分单常量类
 * 
 * @author linqunzhi
 * @date 2018年4月3日
 */
public interface SplitConstants {

    /** 进行中状态 */
    int STATUS_BEGIN = 0;

    /** 已完成状态 */
    int STATUS_FINISH = 1;

    /** 发布到小程序 */
    int PUSH_WX_Y = 1;

    /** 未发布到小程序 */
    int PUSH_WX_N = 0;


    interface SplitIndexConstants {
        
        // 服务进度
        String MODULES_SERVICE="service";

        // 推广提案确认页面
        String MODULES_PROMOTION = "promotion";

        // 推广页面预览
        String MODULES_DELIVIERY = "deliviery";

        // 推广数据报告
        String MODULES_PRESENTATION = "presentation";

        // 评价
        String MODULES_COMMENT = "comment";
    }

}
