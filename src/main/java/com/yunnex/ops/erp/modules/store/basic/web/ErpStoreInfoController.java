package com.yunnex.ops.erp.modules.store.basic.web;

import java.util.HashMap;
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
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.ApiPage;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.order.constans.OrderGoodConstants;
import com.yunnex.ops.erp.modules.order.service.ErpOrderOriginalGoodService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoList;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoParam;
import com.yunnex.ops.erp.modules.store.basic.service.ErpStoreInfoService;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;

import yunnex.common.core.dto.ApiResult;

/**
 * 门店基本信息Controller
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/basic/erpStoreInfo")
public class ErpStoreInfoController extends BaseController {

    @Autowired
	private ErpStoreInfoService erpStoreInfoService;
    @Autowired
    private ErpOrderOriginalGoodService erpOrderOriginalGoodService;

    @Autowired
	private ShopUtils shopUtils;
	
	@ModelAttribute
	public ErpStoreInfo get(@RequestParam(required=false) String id) {
		ErpStoreInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = erpStoreInfoService.get(id);
		}
		if (entity == null){
			entity = new ErpStoreInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("store:basic:erpStoreInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpStoreInfo erpStoreInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ErpStoreInfo> page = erpStoreInfoService.findPage(new Page<ErpStoreInfo>(request, response), erpStoreInfo); 
		model.addAttribute("page", page);
		return "modules/store/basic/erpStoreInfoList";
	}
	
	@RequestMapping(value = {"json", ""})
    public @ResponseBody Page<ErpStoreInfo> listJson(ErpStoreInfo erpStoreInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ErpStoreInfo> page = erpStoreInfoService.findPage(new Page<ErpStoreInfo>(request, response), erpStoreInfo); 
        return page;
    }
	
	@RequestMapping(value = {"json/save"})
    public @ResponseBody String saveJson(ErpStoreInfo erpStoreInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
	    try {
            erpStoreInfoService.save(erpStoreInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "result:fail";
        }
        return "result:success";
    }

	@RequiresPermissions("store:basic:erpStoreInfo:view")
	@RequestMapping(value = "form")
	public String form(ErpStoreInfo erpStoreInfo, Model model) {
		model.addAttribute("erpStoreInfo", erpStoreInfo);
		return "modules/store/basic/erpStoreInfoForm";
	}

	@RequiresPermissions("store:basic:erpStoreInfo:edit")
	@RequestMapping(value = "save")
	public String save(ErpStoreInfo erpStoreInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpStoreInfo)){
			return form(erpStoreInfo, model);
		}
		erpStoreInfoService.save(erpStoreInfo);
        addMessage(redirectAttributes, "保存门店基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreInfo/?repage";
	}
	
	@RequiresPermissions("store:basic:erpStoreInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ErpStoreInfo erpStoreInfo, RedirectAttributes redirectAttributes) {
		erpStoreInfoService.delete(erpStoreInfo);
        addMessage(redirectAttributes, "删除门店基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/store/basic/erpStoreInfo/?repage";
	}
	
    // 门店列表
	@RequestMapping(value = "stores")
    public ResponseEntity<ApiResult<Map<String, Object>>> page(ErpStoreInfo erpStoreInfo, HttpServletRequest request, 
                    HttpServletResponse response, Model model) {
        logger.info("stores start | 门店列表入参 : {}", erpStoreInfo);
	    ErpShopInfo loginShop = shopUtils.getLoginShop();
	    erpStoreInfo.setShopInfoId(loginShop.getId());
	    Page<ErpStoreInfo> page = erpStoreInfoService.findPage(new ApiPage<ErpStoreInfo>(request, response), erpStoreInfo); 
        // 购买聚引客的商品总条数
        int count = erpOrderOriginalGoodService.countByZhangbeiId(loginShop.getZhangbeiId(), OrderGoodConstants.GOOD_TYPE_JU_ID);
        // 是否购买过聚引客商品
        String buyJuYinKeFlag = count > 0 ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("count", page.getCount());
        result.put("list", page.getList());
        result.put("buyJuYinKeFlag", buyJuYinKeFlag);
        ApiResult<Map<String, Object>> apiResult = ApiResult.build(result);
        logger.info("stores end |门店列表结果 : {}", apiResult);
	    return ResponseEntity.ok(apiResult);
	}
	
    // 获取门店信息
    @RequestMapping(value = "get")
    public ResponseEntity<ApiResult<ErpStoreInfoParam>> getErpStoreInfo(String erpStoreInfoId, Integer isMain) {
        logger.info("获取门店信息入参，erpStoreInfoId : {}, isMain : {}", erpStoreInfoId, isMain);
        ErpStoreInfoParam erpStoreInfoParam = erpStoreInfoService.getErpStoreInfo(erpStoreInfoId, isMain);
        ApiResult<ErpStoreInfoParam> apiResult = ApiResult.build(erpStoreInfoParam);
        logger.info("获取门店信息结果: {}", apiResult);
        return ResponseEntity.ok(apiResult);
    }
	
    // 门店资料清单列表
	@RequestMapping(value = "getStoreList")
	public ResponseEntity<ApiResult<Page<ErpStoreInfoList>>> getStoreList(ErpStoreInfoList erpStoreInfoList, HttpServletRequest request, HttpServletResponse response) {
        logger.info("获取门店资料清单列表入参 : {}", erpStoreInfoList);
	    Page<ErpStoreInfoList> page = erpStoreInfoService.getStoreList(new ApiPage<ErpStoreInfoList>(request, response), erpStoreInfoList);
	    ApiResult<Page<ErpStoreInfoList>> apiResult = ApiResult.build(page);
        logger.info("获取门店资料清单列表结果 : {}", apiResult);
	    return ResponseEntity.ok(apiResult);
	}
	
    // 获取门店资料完成状态
	@RequestMapping(value = "getFinishStatus")
	public ResponseEntity<ApiResult<Map<String, Integer>>> getFinishStatus(String erpStoreInfoId) {
        logger.info("获取门店资料完成状态入参，erpStoreInfoId : {}", erpStoreInfoId);
	    Map<String, Integer> finishStatus = erpStoreInfoService.getFinishStatus(erpStoreInfoId);
	    ApiResult<Map<String, Integer>> apiResult = ApiResult.build(finishStatus);
        logger.info("获取门店资料完成状态结果 : {}", apiResult);
	    return ResponseEntity.ok(apiResult);
	}
	
    /**
     * 保存门店基本资料
     * 
     * @date 2018年4月3日
     */
    @ResponseBody
    @RequestMapping(value = "saveBasicInfo", method = RequestMethod.POST)
    public Object saveBasicInfo(@RequestBody ErpStoreInfo erpStoreInfo) {
        ApiResult<ErpStoreInfo> apiResult = ApiResult.build();
        try {
            apiResult.setEntry(erpStoreInfoService.saveBasicInfo(erpStoreInfo));
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("保存失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 查询商户所属门店数量
     * 
     * @date 2018年4月3日
     */
    @ResponseBody
    @RequestMapping(value = "queryStoreCount")
    public Object queryStoreCount() {
        ApiResult<String> apiResult = ApiResult.build();
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        apiResult.setEntry(String.valueOf(loginShop.getStoreCount()));// 获取登录信息中商户的门店数量
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 获取门店基本信息
     *
     * @date 2018年4月9日
     */
    @RequestMapping(value = "getBasic")
    public ResponseEntity<ApiResult<ErpStoreInfoParam>> getBasic(String erpStoreInfoId, Integer isMain) {
        ErpStoreInfoParam erpStoreInfoParam = erpStoreInfoService.getBasic(erpStoreInfoId, isMain);
        ApiResult<ErpStoreInfoParam> apiResult = ApiResult.build(erpStoreInfoParam);
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 获取门店证件信息
     *
     * @date 2018年4月9日
     */
    @RequestMapping(value = "getDocuments")
    public ResponseEntity<ApiResult<ErpStoreInfoParam>> getDocuments(String erpStoreInfoId, Integer isMain) {
        ErpStoreInfoParam erpStoreInfoParam = erpStoreInfoService.getDocuments(erpStoreInfoId, isMain);
        ApiResult<ErpStoreInfoParam> apiResult = ApiResult.build(erpStoreInfoParam);
        return ResponseEntity.ok(apiResult);
    }
    
    /**
     * 获取账号信息
     *
     * @date 2018年4月9日
     */
    @RequestMapping(value = "getAccounts")
    public ResponseEntity<ApiResult<ErpStoreInfoParam>> getAccounts(String erpStoreInfoId, Integer isMain) {
        ErpStoreInfoParam erpStoreInfoParam = erpStoreInfoService.getAccounts(erpStoreInfoId, isMain);
        ApiResult<ErpStoreInfoParam> apiResult = ApiResult.build(erpStoreInfoParam);
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 保存页面编辑标识
     *
     * @date 2018年4月10日
     */
    @RequestMapping(value = "savePageEditTag")
    public Object savePageEditTag(@RequestParam(value = "erpStoreInfoId") String erpStoreInfoId,
                    @RequestParam(value = "pageEditTag") Integer pageEditTag) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            erpStoreInfoService.updatePageEditTag(erpStoreInfoId, pageEditTag);
            apiResult.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("保存失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 保存页面编辑标识
     *
     * @date 2018年4月10日
     */
    @RequestMapping(value = "updatePwd")
    public Object updatePwd(@RequestParam(value = "type") Integer type) {
        ApiResult<String> apiResult = ApiResult.build();
        try {
            erpStoreInfoService.updatePwd(type);
            apiResult.setMessage("修改成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            apiResult.error("修改失败！" + e.getMessage());
        }
        return ResponseEntity.ok(apiResult);
    }

}