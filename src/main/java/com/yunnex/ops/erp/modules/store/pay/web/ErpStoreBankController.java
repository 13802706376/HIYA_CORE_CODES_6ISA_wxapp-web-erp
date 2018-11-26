package com.yunnex.ops.erp.modules.store.pay.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yunnex.ops.erp.common.config.Global;
import com.yunnex.ops.erp.common.persistence.ApiPage;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStoreBank;
import com.yunnex.ops.erp.modules.store.pay.entity.OpenBankEnum;
import com.yunnex.ops.erp.modules.store.pay.service.ErpStoreBankService;

import yunnex.common.core.dto.ApiResult;

/**
 * 银行信息Controller12
 * 
 * @author yunnex
 * @version 2017-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/store/pay/erpStoreBank")
public class ErpStoreBankController extends BaseController {

    @Autowired
	private ErpStoreBankService erpStoreBankService;
	
	@ModelAttribute
	public ErpStoreBank get(@RequestParam(required=false) String id) {
		ErpStoreBank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStoreBankService.get(id);
		}
		if (entity == null){
			entity = new ErpStoreBank();
		}
		return entity;
	}
	
	@RequiresPermissions("store:pay:erpStoreBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStoreBank erpStoreBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStoreBank> page = erpStoreBankService.findPage(new Page<ErpStoreBank>(request, response), erpStoreBank); 
		model.addAttribute("page", page);
		return "modules/store/pay/erpStoreBankList";
	}

	@RequiresPermissions("store:pay:erpStoreBank:view")
	@RequestMapping(value = "form")
	public String form(ErpStoreBank erpStoreBank, Model model) {
		model.addAttribute("erpStoreBank", erpStoreBank);
		return "modules/store/pay/erpStoreBankForm";
	}

	@RequiresPermissions("store:pay:erpStoreBank:edit")
	@RequestMapping(value = "save")
	public String save(ErpStoreBank erpStoreBank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStoreBank)){
			return form(erpStoreBank, model);
		}
		erpStoreBankService.save(erpStoreBank);
        addMessage(redirectAttributes, "保存银行信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/pay/erpStoreBank/?repage";
	}
	
	@RequiresPermissions("store:pay:erpStoreBank:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStoreBank erpStoreBank, RedirectAttributes redirectAttributes) {
		erpStoreBankService.delete(erpStoreBank);
        addMessage(redirectAttributes, "删除银行信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/pay/erpStoreBank/?repage";
	}
	
    // 根据银行卡号模糊查询当前商户的银行信息
	@RequestMapping(value = {"banks"})
    public ResponseEntity<ApiResult<Page<ErpStoreBank>>> banks(ErpStoreBank erpStoreBank, HttpServletRequest request, HttpServletResponse response) {
        logger.info("根据银行卡号模糊查询当前商户的银行信息入参: {}", erpStoreBank);
        Page<ErpStoreBank> page = erpStoreBankService.findShopBanksByCreditCardNo(new ApiPage<ErpStoreBank>(request, response), erpStoreBank); 
        ApiResult<Page<ErpStoreBank>> apiResult = ApiResult.build(page);
        logger.info("根据银行卡号模糊查询当前商户的银行信息结果: {}", apiResult);
        return ResponseEntity.ok(apiResult);
    }
	
    // 查询所有的银行名称
	@RequestMapping(value = {"bankNames"})
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getBankNames(String bankName) {
	    List<Map<String, Object>> list = OpenBankEnum.banks;
	    if (StringUtils.isNotBlank(bankName)) {
            logger.info("查询银行名称入参: {}", bankName);
	        List<Map<String, Object>> searchList = new ArrayList<Map<String,Object>>();
	        for (Map<String, Object> map : list) {
                String name = map.get("name").toString();
                if (name.contains(bankName)) {
                    searchList.add(map);
                }
            }
            logger.info("查询银行名称结果: {}", searchList);
	        return ResponseEntity.ok(ApiResult.build(searchList));
	    }
	    return ResponseEntity.ok(ApiResult.build(list));
	}

    /**
     * 小程序-资料收集-实时保存银行信息
     * 
     * @date 2018年4月4日
     */
    @ResponseBody
    @RequestMapping(value = "saveBankInfo", method = RequestMethod.POST)
    public Object saveBankInfo(@RequestBody ErpStoreBank erpStoreBank) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            apiResult.setEntry(erpStoreBankService.saveBankInfo(erpStoreBank));
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("保存失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

}