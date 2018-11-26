package com.yunnex.ops.erp.modules.workflow.acceptance.web;

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
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.ServiceAcceptanceRequestDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.ServiceAcceptanceResponseDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.service.ErpServiceAcceptanceService;

import yunnex.common.core.dto.ApiResult;

/**
 * 服务验收评价Controller
 * 
 * @author yunnex
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/workflow/acceptance/serviceAcceptance")
public class ErpServiceAcceptanceController extends BaseController {

    @Autowired
    private ErpServiceAcceptanceService serviceAcceptanceService;

    /**
     * 获取服务验收及评分页面数据
     *
     * @param params
     * @return
     * @date 2018年7月13日
     * @author linqunzhi
     */
    @RequestMapping(value = "pageData", method = RequestMethod.POST)
    public @ResponseBody ApiResult<ServiceAcceptanceResponseDto> pageData(@RequestBody Map<String, Object> params) {
        ApiResult<ServiceAcceptanceResponseDto> result = ApiResult.build();
        String visitServiceInfoId = StringUtils.toString(params.get("visitServiceInfoId"), null);
        try {
            ServiceAcceptanceResponseDto dto = serviceAcceptanceService.getServiceAcceptanceResponseDto(visitServiceInfoId);
            result.setEntry(dto);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取首页服务通知 列表数据异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 提交服务验收及评分
     *
     * @param requestDto
     * @return
     * @date 2018年7月13日
     * @author linqunzhi
     */
    @RequestMapping(value = "commit", method = RequestMethod.POST)
    public @ResponseBody ApiResult<String> commit(@RequestBody ServiceAcceptanceRequestDto requestDto) {
        ApiResult<String> result = ApiResult.build();
        try {
            String id = serviceAcceptanceService.commit(requestDto);
            result.setEntry(id);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("提交服务验收及评分异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;

    }
}
