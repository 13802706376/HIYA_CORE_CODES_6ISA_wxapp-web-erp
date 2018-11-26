package com.yunnex.ops.erp.modules.workflow.data.web;

import java.util.List;
import java.util.Map;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
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
import com.yunnex.ops.erp.modules.workflow.data.entity.JykDataPresentation;
import com.yunnex.ops.erp.modules.workflow.data.service.JykDataPresentationService;

import yunnex.common.core.dto.ApiResult;

/**
 * 数据报告对外接口
 * 
 * @author SunQ
 * @date 2018年1月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/api/dataPresentation/")
public class JykDataPresentationApiController extends BaseController {

    @Autowired
    private JykDataPresentationService jykDataPresentationService;
    
    @Value("${domain.wxapp.res}")
    private String UPLOAD_URL;
    
    /**
     * 获取分单相关的数据报告
     *
     * @param params
     * @return
     * @date 2018年1月23日
     * @author SunQ
     */
    @RequestMapping(value="findList", method=RequestMethod.POST)
    @ResponseBody
    public ApiResult<JSONArray> findList(@RequestBody Map<String, Object> params) {
        ApiResult<JSONArray> result = null;
        try {
            logger.info("findList->获取的参数：params={}", params);
            String splitId = null==params.get("splitId") ? "" : params.get("splitId").toString();
            if(StringUtils.isNotBlank(splitId)){
                List<JykDataPresentation> datas = jykDataPresentationService.findListBySplitIdAndState(splitId, "2");
                if(!CollectionUtils.isEmpty(datas)){
                    JSONArray array = new JSONArray(datas.size());
                    for(JykDataPresentation data : datas){
                        JSONObject obj = new JSONObject();
                        obj.put("type", Integer.parseInt(data.getDataType()));
                        obj.put("pdfUrl", UPLOAD_URL + data.getPdfUrl());
                        array.add(obj);
                    }
                    result = ApiResult.build(array);
                    result.setMessage("获取数据成功");
                }else{
                    result = ApiResult.build(null);
                    result.setMessage("没有数据");
                }
                result.setCode("0");
                result.setMessage("请求成功");
            }else{
                result = ApiResult.build(null);
                result.setCode("1");
                result.setMessage("splitId不能为空");
            }
        } catch (RuntimeException e) {
            result = ApiResult.build(null);
            result.setCode("1");
            result.setMessage("系统异常");
            logger.info("获取分单相关的数据报告findList->出现异常：{}", e.getMessage());
            logger.error(e.getMessage(), e);
        }
        logger.info("findList->返回的参数：returns={}", result);
        return result;
    }
}
