package com.yunnex.ops.erp.modules.message.web;

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
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageAllRequestDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageAllResponseDto;
import com.yunnex.ops.erp.modules.message.service.ErpServiceMessageService;
import com.yunnex.ops.erp.modules.workflow.enums.FlowServiceType;

import yunnex.common.core.dto.ApiResult;

/**
 * 服务通知表Controller
 * 
 * @author yunnex
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/message/serviceMessage")
public class ErpServiceMessageController extends BaseController {

    @Autowired
    private ErpServiceMessageService erpServiceMessageService;

    /**
     * 获取服务通知列表数据
     *
     * @param params
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    @RequestMapping(value = "findList", method = RequestMethod.POST)
    public @ResponseBody ApiResult<ServiceMessageAllResponseDto> findList(@RequestBody ServiceMessageAllRequestDto requestDto) {
        ApiResult<ServiceMessageAllResponseDto> result = ApiResult.build();
        try {
            ServiceMessageAllResponseDto dto = erpServiceMessageService.getServiceMessageAllResponseDto(requestDto);
            result.setEntry(dto);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取服务通知列表数据异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取服务通知列表参数
     *
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    @RequestMapping(value = "queryParam", method = RequestMethod.POST)
    public @ResponseBody ApiResult<String> queryParam() {
        ApiResult<String> result = ApiResult.build();
        try {
            String json = getQueryParamJson();
            result.setEntry(json);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取服务通知列表参数数据异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取请求参数数据
     *
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    private static String getQueryParamJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"serviceTypeList\":[");
        for (FlowServiceType em : FlowServiceType.values()) {
            builder.append("{");
            builder.append("\"").append("type").append("\":\"").append(em.getType()).append("\"");
            builder.append(",\"").append("name").append("\":\"").append(em.getName()).append("\"");
            builder.append("}");
        }
        builder.append("]}");
        return builder.toString();
    }

    /**
     * 关闭通知
     *
     * @param params
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    @RequestMapping(value = "close", method = RequestMethod.POST)
    public @ResponseBody ApiResult<String> close(@RequestBody Map<String, Object> params) {
        String id = StringUtils.toString(params.get("id"), null);
        ApiResult<String> result = ApiResult.build();
        try {
            erpServiceMessageService.close(id);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("关闭服务通知异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

}
