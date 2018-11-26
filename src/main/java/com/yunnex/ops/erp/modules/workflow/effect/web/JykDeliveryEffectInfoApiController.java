package com.yunnex.ops.erp.modules.workflow.effect.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.workflow.effect.entity.JykDeliveryEffectInfo;
import com.yunnex.ops.erp.modules.workflow.effect.service.JykDeliveryEffectInfoService;

import yunnex.common.core.dto.ApiResult;

@Controller
@RequestMapping(value = "${adminPath}/api/deliveryEffect/")
public class JykDeliveryEffectInfoApiController extends BaseController {

    @Autowired
    private JykDeliveryEffectInfoService jykDeliveryEffectInfoService;
    
    @Value("${domain.wxapp.res}")
    private String UPLOAD_URL;
    
    /**
     * 获取分单相关的投放效果
     *
     * @param params
     * @return
     * @date 2018年1月25日
     * @author SunQ
     */
    @RequestMapping(value="get", method=RequestMethod.POST)
    @ResponseBody
    public ApiResult<JSONObject> get(@RequestBody Map<String, Object> params) {
        ApiResult<JSONObject> result = null;
        try {
            logger.info("get->获取的参数：params={}", params);
            String splitId = null==params.get("splitId") ? "" : params.get("splitId").toString();
            if(StringUtils.isNotBlank(splitId)){
                JykDeliveryEffectInfo deliveryEffectInfo = jykDeliveryEffectInfoService.getBySplitId(splitId);
                if(deliveryEffectInfo!=null){
                    JSONObject obj = new JSONObject();
                    obj.put("deliveryId", deliveryEffectInfo.getId());
                    obj.put("state", Integer.parseInt(deliveryEffectInfo.getState()));
                    
                    JSONArray array = new JSONArray();
                    if(StringUtils.isNotBlank(deliveryEffectInfo.getOuterImgUrlFriends()) && StringUtils.isNotBlank(deliveryEffectInfo.getInnerImgUrlFriends())){
                        JSONObject objson = new JSONObject();
                        objson.put("type", 1);
                        objson.put("outerImgUrl", UPLOAD_URL + deliveryEffectInfo.getOuterImgUrlFriends());
                        objson.put("outerImgName", deliveryEffectInfo.getOuterImgNameFriends());
                        objson.put("innerImgUrl", UPLOAD_URL + deliveryEffectInfo.getInnerImgUrlFriends());
                        objson.put("innerImgName", deliveryEffectInfo.getInnerImgNameFriends());
                        array.add(objson);
                    }
                    
                    if(StringUtils.isNotBlank(deliveryEffectInfo.getOuterImgUrlWeibo()) && StringUtils.isNotBlank(deliveryEffectInfo.getInnerImgUrlWeibo())){
                        JSONObject objson = new JSONObject();
                        objson.put("type", 2);
                        objson.put("outerImgUrl", UPLOAD_URL + deliveryEffectInfo.getOuterImgUrlWeibo());
                        objson.put("outerImgName", deliveryEffectInfo.getOuterImgNameWeibo());
                        objson.put("innerImgUrl", UPLOAD_URL + deliveryEffectInfo.getInnerImgUrlWeibo());
                        objson.put("innerImgName", deliveryEffectInfo.getInnerImgNameWeibo());
                        array.add(objson);
                    }
                    
                    if(StringUtils.isNotBlank(deliveryEffectInfo.getOuterImgUrlMomo()) && StringUtils.isNotBlank(deliveryEffectInfo.getInnerImgUrlMomo())){
                        JSONObject objson = new JSONObject();
                        objson.put("type", 3);
                        objson.put("outerImgUrl", UPLOAD_URL + deliveryEffectInfo.getOuterImgUrlMomo());
                        objson.put("outerImgName", deliveryEffectInfo.getOuterImgNameMomo());
                        objson.put("innerImgUrl", UPLOAD_URL + deliveryEffectInfo.getInnerImgUrlMomo());
                        objson.put("innerImgName", deliveryEffectInfo.getInnerImgNameMomo());
                        array.add(objson);
                    }
                    
                    obj.put("channels", array);
                    result = ApiResult.build(obj);
                    result.setMessage("获取数据成功");
                }else{
                    result = ApiResult.build(null);
                    result.setMessage("没有数据");
                }
            }else{
                result = ApiResult.build(null);
                result.setCode("1");
                result.setMessage("splitId不能为空");
            }
        } catch (RuntimeException e) {
            result = ApiResult.build(null);
            result.setCode("1");
            result.setMessage("系统异常");
            logger.info("获取分单相关的投放效果get->出现异常：{}", e.getMessage());
            logger.error(e.getMessage(), e);
        }
        logger.info("get->返回的参数：returns={}", result);
        return result;
    }
    
    /**
     * 确认投放操作
     *
     * @param params
     * @return
     * @date 2018年1月25日
     * @author SunQ
     */
    @RequestMapping(value="confirmDelivery", method=RequestMethod.POST)
    @ResponseBody
    public ApiResult<JSONObject> confirmDelivery(@RequestBody Map<String, Object> params) {
        ApiResult<JSONObject> result = null;
        try {
            logger.info("confirmDelivery->获取的参数：params={}", params);
            String splitId = null==params.get("splitId") ? "" : params.get("splitId").toString();
            String deliveryId = null==params.get("deliveryId") ? "" : params.get("deliveryId").toString();
            if(StringUtils.isNotBlank(splitId) && StringUtils.isNotBlank(deliveryId)){
                jykDeliveryEffectInfoService.updateState(deliveryId, "3");
                result = ApiResult.build(null);
                result.setMessage("操作成功");
            }else{
                result = ApiResult.build(null);
                result.setCode("1");
                result.setMessage("splitId和deliveryId不能为空");
            }
        } catch (RuntimeException e) {
            result = ApiResult.build(null);
            result.setCode("1");
            result.setMessage("系统异常");
            logger.info("确认投放出现->出现异常：{}", e.getMessage());
        }
        logger.info("confirmDelivery->返回的参数：return={}", result);
        return result;
    }
}
