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
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreLegalPerson;
import com.yunnex.ops.erp.modules.store.basic.service.ErpStoreLegalPersonService;

import yunnex.common.core.dto.ApiResult;

/**
 * 法人信息Controller
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/basic/erpStoreLegalPerson")
public class ErpStoreLegalPersonController extends BaseController {

    @Autowired
	private ErpStoreLegalPersonService erpStoreLegalPersonService;
	
	@ModelAttribute
	public ErpStoreLegalPerson get(@RequestParam(required=false) String id) {
		ErpStoreLegalPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStoreLegalPersonService.get(id);
		}
		if (entity == null){
			entity = new ErpStoreLegalPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("store:basic:erpStoreLegalPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStoreLegalPerson erpStoreLegalPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStoreLegalPerson> page = erpStoreLegalPersonService.findPage(new Page<ErpStoreLegalPerson>(request, response), erpStoreLegalPerson); 
		model.addAttribute("page", page);
		return "modules/store/basic/erpStoreLegalPersonList";
	}

	@RequiresPermissions("store:basic:erpStoreLegalPerson:view")
	@RequestMapping(value = "form")
	public String form(ErpStoreLegalPerson erpStoreLegalPerson, Model model) {
		model.addAttribute("erpStoreLegalPerson", erpStoreLegalPerson);
		return "modules/store/basic/erpStoreLegalPersonForm";
	}

	@RequiresPermissions("store:basic:erpStoreLegalPerson:edit")
	@RequestMapping(value = "save")
	public String save(ErpStoreLegalPerson erpStoreLegalPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStoreLegalPerson)){
			return form(erpStoreLegalPerson, model);
		}
		erpStoreLegalPersonService.save(erpStoreLegalPerson);
        addMessage(redirectAttributes, "保存法人信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreLegalPerson/?repage";
	}
	
	@RequiresPermissions("store:basic:erpStoreLegalPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStoreLegalPerson erpStoreLegalPerson, RedirectAttributes redirectAttributes) {
		erpStoreLegalPersonService.delete(erpStoreLegalPerson);
        addMessage(redirectAttributes, "删除法人信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreLegalPerson/?repage";
	}

    /**
     * 小程序-资料收集-实时保存门店法人信息
     * 
     * @date 2018年4月4日
     */
    @ResponseBody
    @RequestMapping(value = "saveLegalPerson", method = RequestMethod.POST)
    public Object saveLegalPerson(@RequestBody ErpStoreLegalPerson erpStoreLegalPerson) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            apiResult.setEntry(erpStoreLegalPersonService.saveLegalPerson(erpStoreLegalPerson));
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("保存失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

}