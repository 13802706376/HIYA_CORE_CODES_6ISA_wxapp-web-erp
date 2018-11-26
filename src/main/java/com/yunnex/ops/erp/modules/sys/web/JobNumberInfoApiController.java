package com.yunnex.ops.erp.modules.sys.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;
import com.yunnex.ops.erp.modules.sys.service.JobNumberInfoService;
import com.yunnex.ops.erp.modules.workflow.user.entity.ErpOrderFlowUser;
import com.yunnex.ops.erp.modules.workflow.user.service.ErpOrderFlowUserService;

import yunnex.common.core.dto.ApiResult;

/**
 * 工号信息接口Controller
 * 
 * @author SunQ
 * @date 2018年1月24日
 */
@Controller
@RequestMapping(value = "${adminPath}/api/jobNumber/")
public class JobNumberInfoApiController extends BaseController {
    
    /**
     * 工号管理Service
     */
    @Autowired
    private JobNumberInfoService jobNumberService;
    
    /**
     * 分单处理Service
     */
    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    
    /**
     * 订单处理人员Service
     */
    @Autowired
    private ErpOrderFlowUserService erpOrderFlowUserService;
    
    @Value("${domain.wxapp.res}")
    private String UPLOAD_URL;

    @RequestMapping(value="getJobNumber", method=RequestMethod.POST)
    @ResponseBody
    public ApiResult<JSONObject> getJobNumber(@RequestBody Map<String, Object> params) {
        ApiResult<JSONObject> result = null;
        try {
            logger.info("getJobNumber->获取的参数：params={}", params);
            String userId = null==params.get("userId") ? "" : params.get("userId").toString();
            if(StringUtils.isNotBlank(userId)){
                JobNumberInfo jobNumber = jobNumberService.getByUserId(userId);
                if(jobNumber!=null){
                    JSONObject obj = new JSONObject();
                    obj.put("jobNumber", jobNumber.getJobNumber());
                    obj.put("name", jobNumber.getUserName());
                    obj.put("roleName", jobNumber.getRoleName());
                    obj.put("score", jobNumber.getScore());
                    obj.put("telephone", jobNumber.getTelephone());
                    obj.put("iconImg", UPLOAD_URL + jobNumber.getIconImg());
                    result = ApiResult.build(obj);
                    result.setCode("0");
                    result.setMessage("请求成功");
                }else{
                    result = ApiResult.build(null);
                    result.setCode("1");
                    result.setMessage("找不到数据");
                }
            }else{
                result = ApiResult.build(null);
                result.setCode("1");
                result.setMessage("userId不能为空");
            }
        } catch (RuntimeException e) {
            result = ApiResult.build(null);
            result.setCode("1");
            result.setMessage("系统异常");
            logger.info("getJobNumber->出现异常：{}", e.getMessage());
            logger.error(e.getMessage(), e);
        }
        logger.info("getJobNumber->返回的参数：returns={}", result);
        return result;
    }
    
    @RequestMapping(value="getJobNumberBySplit", method=RequestMethod.POST)
    @ResponseBody
    public ApiResult<JSONObject> getJobNumberBySplit(@RequestBody Map<String, Object> params) {
        ApiResult<JSONObject> result = null;
        try {
            logger.info("getJobNumber2->获取的参数：params={}", params);
            String splitId = null==params.get("splitId") ? "" : params.get("splitId").toString();
            String role = null==params.get("role") ? "" : params.get("role").toString();
            if(StringUtils.isNotBlank(splitId) && StringUtils.isNotBlank(role)){
                ErpOrderSplitInfo splitInfo = erpOrderSplitInfoService.get(splitId);
                if(splitInfo!=null){
                    ErpOrderFlowUser user = erpOrderFlowUserService.findByProcInsIdAndRoleName(splitInfo.getProcInsId(), role);
                    if(user!=null){
                        JobNumberInfo jobNumber = jobNumberService.getByUserId(user.getUser().getId());
                        if(jobNumber!=null){
                            JSONObject obj = new JSONObject();
                            obj.put("jobNumber", jobNumber.getJobNumber());
                            obj.put("name", jobNumber.getUserName());
                            obj.put("roleName", jobNumber.getRoleName());
                            obj.put("score", jobNumber.getScore());
                            obj.put("telephone", jobNumber.getTelephone());
                            obj.put("iconImg", UPLOAD_URL + jobNumber.getIconImg());
                            result = ApiResult.build(obj);
                            result.setCode("0");
                            result.setMessage("请求成功");
                        }else{
                            result = ApiResult.build(null);
                            result.setCode("1");
                            result.setMessage("找不到数据");
                        }
                    }else{
                        result = ApiResult.build(null);
                        result.setCode("1");
                        result.setMessage("角色不存在");
                    }
                }else{
                    result = ApiResult.build(null);
                    result.setCode("1");
                    result.setMessage("订单不存在");
                }
            }else{
                result = ApiResult.build(null);
                result.setCode("1");
                result.setMessage("splitId和role不能为空");
            }
        } catch (RuntimeException e) {
            result = ApiResult.build(null);
            result.setCode("1");
            result.setMessage("系统异常");
            logger.info("getJobNumber2->出现异常：{}", e.getMessage());
            logger.error(e.getMessage(), e);
        }
        logger.info("getJobNumber2->返回的参数：returns={}", result);
        return result;
    }
}
