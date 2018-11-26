package com.yunnex.ops.erp.modules.message.web;

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
import com.yunnex.ops.erp.modules.message.dto.ServiceScheduleResponseDto;
import com.yunnex.ops.erp.modules.message.service.ErpServiceProgressService;

import yunnex.common.core.dto.ApiResult;

/**
 * 服务进度表Controller
 * 
 * @author yunnex
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/message/serviceSchedule")
public class ErpServiceProgressController extends BaseController {

    @Autowired
    private ErpServiceProgressService serviceScheduleService;

    /**
     * 获取流程进度详情
     *
     * @param procInsId
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    @RequestMapping(value = "processInfo", method = RequestMethod.POST)
    public @ResponseBody ApiResult<List<ServiceScheduleResponseDto>> processInfo(@RequestBody Map<String, Object> params) {
        ApiResult<List<ServiceScheduleResponseDto>> result = ApiResult.build();
        String procInsId = StringUtils.toString(params.get("procInsId"), null);
        String serviceType = StringUtils.toString(params.get("serviceType"), null);
        try {
            List<ServiceScheduleResponseDto> list = serviceScheduleService.getProcessInfo(procInsId, serviceType);
            result.setEntry(list);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取流程进度详情异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }
}
