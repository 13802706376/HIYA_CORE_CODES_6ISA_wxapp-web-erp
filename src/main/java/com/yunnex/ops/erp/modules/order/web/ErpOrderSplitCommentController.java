package com.yunnex.ops.erp.modules.order.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.order.param.SplitCommentParam;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitCommentService;
import com.yunnex.ops.erp.modules.order.view.SplitCommentQuetionConfView;

import yunnex.common.core.dto.ApiResult;

/**
 * 聚引客分单评论Controller
 * 
 * @author yunnex
 * @version 2018-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/order/erpOrderSplitComment")
public class ErpOrderSplitCommentController extends BaseController {

    @Autowired
    private ErpOrderSplitCommentService erpOrderSplitCommentService;

    /**
     * 提交分单评价
     *
     * @param comment
     * @return
     * @date 2018年1月30日
     * @author hsr
     */
    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public @ResponseBody ApiResult<Void> comment(@RequestBody SplitCommentParam splitCommentParam) {
        ApiResult<Void> result = ApiResult.build();
        try {
            erpOrderSplitCommentService.comment(splitCommentParam);
            result.setMessage("保存成功！");
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info(" 提交分单评价异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取评价问题列表配置信息
     *
     * @return
     * @date 2018年4月4日
     * @author linqunzhi
     */
    @RequestMapping(value = "questionList", method = RequestMethod.POST)
    public @ResponseBody ApiResult<List<SplitCommentQuetionConfView>> questionList() {
        ApiResult<List<SplitCommentQuetionConfView>> result = ApiResult.build();
        try {
            List<SplitCommentQuetionConfView> list = erpOrderSplitCommentService.findCommentConfViewList();
            result.setEntry(list);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取评价问题列表配置信息异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

}
