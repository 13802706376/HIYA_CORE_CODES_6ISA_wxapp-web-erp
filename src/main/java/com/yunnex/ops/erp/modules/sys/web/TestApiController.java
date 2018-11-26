package com.yunnex.ops.erp.modules.sys.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.good.category.entity.ErpGoodCategory;
import com.yunnex.ops.erp.modules.good.entity.ErpGoodInfo;


@Controller
@RequestMapping(value = "test")
public class TestApiController extends BaseController {


    @RequestMapping("qry")
    @ResponseBody
    public JSONObject qry(@RequestBody String jsonStr) {
        JSONObject resObject = new JSONObject();
        logger.info("接收到地推的订单推送信息为:"+jsonStr);
        resObject.put("code", 200);
        resObject.put("msg", "OK");
        logger.info("处理结果:OK");
        return resObject;
    }
    
    @RequestMapping("deployment")
    public void deployment(ErpGoodInfo erpGoodInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException 
    {
        model.addAttribute("goodCateGoryList", "");
        response.getWriter().print("<div style='font-size:18px;font-family:微软雅黑;color:green;text-align:center;margin-top:20px;'>Wxapp-web-erp application deployment successfully!</div>");
    }
}
