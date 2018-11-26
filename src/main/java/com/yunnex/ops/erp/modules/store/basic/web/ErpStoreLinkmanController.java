package com.yunnex.ops.erp.modules.store.basic.web;

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
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreLinkman;
import com.yunnex.ops.erp.modules.store.basic.service.ErpStoreLinkmanService;

import yunnex.common.core.dto.ApiResult;

/**
 * 门店联系人信息Controller
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/basic/erpStoreLinkman")
public class ErpStoreLinkmanController extends BaseController {

    @Autowired
	private ErpStoreLinkmanService erpStoreLinkmanService;
	
	@ModelAttribute
	public ErpStoreLinkman get(@RequestParam(required=false) String id) {
		ErpStoreLinkman entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStoreLinkmanService.get(id);
		}
		if (entity == null){
			entity = new ErpStoreLinkman();
		}
		return entity;
	}
	
	@RequiresPermissions("store:basic:erpStoreLinkman:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStoreLinkman erpStoreLinkman, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStoreLinkman> page = erpStoreLinkmanService.findPage(new Page<ErpStoreLinkman>(request, response), erpStoreLinkman); 
		model.addAttribute("page", page);
		return "modules/store/basic/erpStoreLinkmanList";
	}

	@RequiresPermissions("store:basic:erpStoreLinkman:view")
	@RequestMapping(value = "form")
	public String form(ErpStoreLinkman erpStoreLinkman, Model model) {
		model.addAttribute("erpStoreLinkman", erpStoreLinkman);
		return "modules/store/basic/erpStoreLinkmanForm";
	}

	@RequiresPermissions("store:basic:erpStoreLinkman:edit")
	@RequestMapping(value = "save")
	public String save(ErpStoreLinkman erpStoreLinkman, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStoreLinkman)){
			return form(erpStoreLinkman, model);
		}
		erpStoreLinkmanService.save(erpStoreLinkman);
        addMessage(redirectAttributes, "保存门店联系人信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreLinkman/?repage";
	}
	
	@RequiresPermissions("store:basic:erpStoreLinkman:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStoreLinkman erpStoreLinkman, RedirectAttributes redirectAttributes) {
		erpStoreLinkmanService.delete(erpStoreLinkman);
        addMessage(redirectAttributes, "删除门店联系人信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreLinkman/?repage";
	}

    /**
     * 保存门店联系人信息
     * 
     * @date 2018年4月4日
     */
    @ResponseBody
    @RequestMapping(value = "saveLinkman", method = RequestMethod.POST)
    public Object saveLinkman(@RequestBody ErpStoreLinkman erpStoreLinkman) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            apiResult.setEntry(erpStoreLinkmanService.saveLinkman(erpStoreLinkman));
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("保存失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

}