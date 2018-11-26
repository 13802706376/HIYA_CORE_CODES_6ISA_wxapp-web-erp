package com.yunnex.ops.erp.modules.store.advertiser.web;

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
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserFriends;
import com.yunnex.ops.erp.modules.store.advertiser.service.ErpStoreAdvertiserFriendsService;

import yunnex.common.core.dto.ApiResult;

/**
 * 朋友圈广告主开通资料Controller
 * @author yunnex
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/advertiser/erpStoreAdvertiserFriends")
public class ErpStoreAdvertiserFriendsController extends BaseController {
    
	@Autowired
	private ErpStoreAdvertiserFriendsService erpStoreAdvertiserFriendsService;
	
	@ModelAttribute
	public ErpStoreAdvertiserFriends get(@RequestParam(required=false) String id) {
		ErpStoreAdvertiserFriends entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStoreAdvertiserFriendsService.get(id);
		}
		if (entity == null){
			entity = new ErpStoreAdvertiserFriends();
		}
		return entity;
	}
	
	@RequiresPermissions("store:advertiser:erpStoreAdvertiserFriends:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStoreAdvertiserFriends> page = erpStoreAdvertiserFriendsService.findPage(new Page<ErpStoreAdvertiserFriends>(request, response), erpStoreAdvertiserFriends); 
		model.addAttribute("page", page);
		return "modules/store/advertiser/erpStoreAdvertiserFriendsList";
	}

	@RequiresPermissions("store:advertiser:erpStoreAdvertiserFriends:view")
	@RequestMapping(value = "form")
	public String form(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends, Model model) {
		model.addAttribute("erpStoreAdvertiserFriends", erpStoreAdvertiserFriends);
		return "modules/store/advertiser/erpStoreAdvertiserFriendsForm";
	}

	@RequiresPermissions("store:advertiser:erpStoreAdvertiserFriends:edit")
	@RequestMapping(value = "save")
	public String save(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStoreAdvertiserFriends)){
			return form(erpStoreAdvertiserFriends, model);
		}
		erpStoreAdvertiserFriendsService.save(erpStoreAdvertiserFriends);
		addMessage(redirectAttributes, "保存朋友圈广告主开通资料成功");
		return "redirect:"+Global.getAdminPath()+"/store/advertiser/erpStoreAdvertiserFriends/?repage";
	}
	
	@RequiresPermissions("store:advertiser:erpStoreAdvertiserFriends:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends, RedirectAttributes redirectAttributes) {
		erpStoreAdvertiserFriendsService.delete(erpStoreAdvertiserFriends);
		addMessage(redirectAttributes, "删除朋友圈广告主开通资料成功");
		return "redirect:"+Global.getAdminPath()+"/store/advertiser/erpStoreAdvertiserFriends/?repage";
	}
	
	@RequestMapping(value = "getAdvInfo")
	public @ResponseBody ResponseEntity<ApiResult<ErpStoreAdvertiserFriends>> getErpStoreInfo(String erpStoreInfoId, Integer isMain) {
	    logger.info("获取门店朋友圈广告主开通资料入参，erpStoreInfoId : {}, isMain : {}", erpStoreInfoId, isMain);
	    ErpStoreAdvertiserFriends advInfo = erpStoreAdvertiserFriendsService.getAdvInfo(erpStoreInfoId, isMain);
	    ApiResult<ErpStoreAdvertiserFriends> apiResult = ApiResult.build(advInfo);
	    logger.info("获取门店朋友圈广告主开通资料结果: {}", apiResult);
	    return ResponseEntity.ok(apiResult);
	}
	
	@RequestMapping(value = "saveInfo", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ApiResult<String>> saveAdvInfo(@RequestBody ErpStoreAdvertiserFriends advInfo) {
	    logger.info("保存门店朋友圈广告主开通资料入参：{}", advInfo);
	    ApiResult<String> apiResult = ApiResult.build();
        try {
            erpStoreAdvertiserFriendsService.saveInfo(advInfo);
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            apiResult.error("保存失败！" + e.getMessage());
            logger.error("保存门店朋友圈广告主开通资料失败！", e);
        }
        logger.info("保存门店朋友圈广告主开通资料结果：{}", apiResult);
	    return ResponseEntity.ok(apiResult);
	}

}