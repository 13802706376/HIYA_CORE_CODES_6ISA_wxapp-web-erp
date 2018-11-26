package com.yunnex.ops.erp.modules.store.pay.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yunnex.ops.erp.common.config.Global;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;
import com.yunnex.ops.erp.modules.store.pay.service.ErpStorePayUnionpayService;

import yunnex.common.core.dto.ApiResult;

/**
 * 银联支付开通资料Controller
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/pay/erpStorePayUnionpay")
public class ErpStorePayUnionpayController extends BaseController {

	@Autowired
	private ErpStorePayUnionpayService erpStorePayUnionpayService;
	
	@ModelAttribute
	public ErpStorePayUnionpay get(@RequestParam(required=false) String id) {
		ErpStorePayUnionpay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStorePayUnionpayService.get(id);
		}
		if (entity == null){
			entity = new ErpStorePayUnionpay();
		}
		return entity;
	}
	
	@RequiresPermissions("store:pay:erpStorePayUnionpay:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStorePayUnionpay erpStorePayUnionpay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStorePayUnionpay> page = erpStorePayUnionpayService.findPage(new Page<ErpStorePayUnionpay>(request, response), erpStorePayUnionpay); 
		model.addAttribute("page", page);
		return "modules/store/pay/erpStorePayUnionpayList";
	}

	@RequiresPermissions("store:pay:erpStorePayUnionpay:view")
	@RequestMapping(value = "form")
	public String form(ErpStorePayUnionpay erpStorePayUnionpay, Model model) {
		model.addAttribute("erpStorePayUnionpay", erpStorePayUnionpay);
		return "modules/store/pay/erpStorePayUnionpayForm";
	}

	@RequiresPermissions("store:pay:erpStorePayUnionpay:edit")
	@RequestMapping(value = "save")
	public String save(ErpStorePayUnionpay erpStorePayUnionpay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStorePayUnionpay)){
			return form(erpStorePayUnionpay, model);
		}
		erpStorePayUnionpayService.save(erpStorePayUnionpay);
        addMessage(redirectAttributes, "保存银联支付开通资料成功");
		return "redirect:"+Global.getAdminPath()+"/store/pay/erpStorePayUnionpay/?repage";
	}
	
	@RequiresPermissions("store:pay:erpStorePayUnionpay:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStorePayUnionpay erpStorePayUnionpay, RedirectAttributes redirectAttributes) {
		erpStorePayUnionpayService.delete(erpStorePayUnionpay);
        addMessage(redirectAttributes, "删除银联支付开通资料成功");
		return "redirect:"+Global.getAdminPath()+"/store/pay/erpStorePayUnionpay/?repage";
	}
	
    /**
     * 查询银联支付信息（现阶段只查询门店照片）
     *
     * @date 2018年4月9日
     */
	@RequestMapping(value = "getPayInfo", method = RequestMethod.GET)
    public ResponseEntity<ApiResult<ErpStorePayUnionpay>> getPayInfo(String erpStoreInfoId, Integer isMain) {
	    ErpStorePayUnionpay payInfo = erpStorePayUnionpayService.getPayInfo(erpStoreInfoId, isMain);
        ApiResult<ErpStorePayUnionpay> apiResult = ApiResult.build(payInfo);
        return ResponseEntity.ok(apiResult);
    }
	
    /**
     * 保存银联支付资料
     *
     * @date 2018年4月8日
     */
	@RequestMapping(value = "saveInfo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult<String>> savePayInfo(@RequestBody ErpStorePayUnionpay payInfo) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            apiResult.setEntry(erpStorePayUnionpayService.saveInfo(payInfo));
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            apiResult.error("保存失败！" + e.getMessage());
            logger.error("保存门店银联支付开通资料失败！", e);
        }
        return ResponseEntity.ok(apiResult);
    }

}