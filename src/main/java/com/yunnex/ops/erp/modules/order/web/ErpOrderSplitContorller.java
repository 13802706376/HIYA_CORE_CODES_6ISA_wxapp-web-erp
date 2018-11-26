package com.yunnex.ops.erp.modules.order.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;

import yunnex.common.core.dto.ApiResult;

@Controller
@RequestMapping(value = "${adminPath}/order/erpOrderSplit")
public class ErpOrderSplitContorller extends BaseController{
    
    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    
    /**
     * 获取分单首页
     *
     * @param map
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     */
    @RequestMapping(value = "index",method=RequestMethod.POST )
    public @ResponseBody ApiResult<List<String>> index(@RequestBody Map<String,Object> map){
        String splitId = StringUtils.toString(map.get("splitId"), null);
        ApiResult<List<String>> result = ApiResult.build();
        try {
            List<String> list = erpOrderSplitInfoService.getIndexOpenList(splitId);
            result.setEntry(list);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取分单首页", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 查看效果报告
     *
     * @param params
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    @RequestMapping(value = "lookEffect", method = RequestMethod.POST)
    public @ResponseBody ApiResult<String> lookEffect(@RequestBody Map<String, Object> params) {
        String id = StringUtils.toString(params.get("id"), null);
        ApiResult<String> result = ApiResult.build();
        try {
            erpOrderSplitInfoService.lookEffect(id);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("查看过效果报告异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }


}
