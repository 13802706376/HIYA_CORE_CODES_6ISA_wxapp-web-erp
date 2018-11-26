package com.yunnex.ops.erp.modules.workflow.flow.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.workflow.flow.service.WorkFlowService;

import yunnex.common.core.dto.ApiResult;

@Controller
@RequestMapping("${adminPath}/workflow")
public class WorkFlowController extends BaseController {
    
    @Autowired
    private WorkFlowService workFlowService;
    
    @RequestMapping("/diagnosisInfo")
    public @ResponseBody ApiResult<Map<String, Object>> getDiagnosisInfo(@RequestParam(required = true) String splitId,
                    @RequestParam(required = false) String roleId) {
        logger.info("获取经营诊断方案策划数据入参: splitId = {}, roleId = {}", splitId, roleId);
        ApiResult<Map<String, Object>> apiResult = ApiResult.build();
        try {
            Map<String, Object> diagnosisInfo = workFlowService.getDiagnosisInfo(splitId, roleId);
            apiResult.setEntry(diagnosisInfo);
            apiResult.setCode((String) diagnosisInfo.get("errorCode"));
            apiResult.setMessage((String) diagnosisInfo.get("errorMsg"));
        } catch (Exception e) {
            apiResult.setCode("-1");
            apiResult.setMessage(e.getMessage());
            logger.error("获取经营诊断方案策划数据异常！", e);
        }
        logger.info("获取经营诊断方案策划数据结果: {}", apiResult);
        return apiResult;
    }
    
}
