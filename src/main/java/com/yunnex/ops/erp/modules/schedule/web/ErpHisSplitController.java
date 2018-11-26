package com.yunnex.ops.erp.modules.schedule.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.schedule.entity.ErpHisSplit;
import com.yunnex.ops.erp.modules.schedule.entity.ErpHisSplitValue;
import com.yunnex.ops.erp.modules.schedule.enums.ErpHisSplitType;
import com.yunnex.ops.erp.modules.schedule.service.ErpHisSplitService;
import com.yunnex.ops.erp.modules.schedule.service.ErpHisSplitValueService;

import yunnex.common.core.dto.ApiResult;

/**
 * 生产进度小程序父表Controller
 * @author pengchenghe
 * @version 2018-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/api/schedule")
public class ErpHisSplitController extends BaseController {

	@Autowired
	private ErpHisSplitService erpHisSplitService;
	@Autowired
	private ErpHisSplitValueService valueService;
    /**
     *未开始的订单
     * 
     */
    @RequestMapping(value = "findAllSchedule", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<List<ErpHisSplit>> findAllSchedule(@RequestBody Map<String,Object> params,HttpServletResponse response) {
    	String splitId=params.get("splitId").toString();
    	List<ErpHisSplit> hisSplit=erpHisSplitService.findAllListWhereSplitId(splitId);
    	if(null!=hisSplit&&!hisSplit.isEmpty()){
    		List<ErpHisSplitValue> v=null;
    		for(int i=0;i<hisSplit.size();i++){
                ErpHisSplit his = hisSplit.get(i);
                // 之前代码写死tilte值入数据库，现在按类型来获取tilte
                his.setProcessTitle(ErpHisSplitType.getNameByType(his.getProcessType()));
                v = valueService.findAllListWhereHisId(his.getId());
                his.getHisSplitValue().addAll(v);
    		}
    		return ApiResult.build(hisSplit);
    	}
    	ApiResult<List<ErpHisSplit>> result = ApiResult.build(null);
    	result.setCode("1");
        return result;
 
    }

}