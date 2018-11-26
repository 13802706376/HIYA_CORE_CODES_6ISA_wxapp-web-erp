package com.yunnex.ops.erp.modules.workflow.flow.from;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 任务查询列表
 * 
 * @author yunnex
 * @date 2017年10月31日
 */
public class WorkFlowQueryForm {

    /** 订单编号 */
    private String orderNumber;
    /** 任务状态 */
    private String taskStateList;
    /** 商户名称 */
    private String shopName;
    /** 是否加急 */
    private String hurryFlag;
    /** 商品编号 */
    private String goodsType;
    
    private String assignee;
    // 字符串转换后的结果
    List<String> taskStates;
    List<String> goodTypes;
    
    private Integer isPage = 1; // 是否分页，1：是，0：否
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer total;      // 总数
    private Integer pageTotal;
    private Integer startIndex;

    
 // 字符串转换成查询条件
    public List<String> getTaskStates() {
        return StringUtils.isNotBlank(getTaskStateList()) ? Arrays.asList(getTaskStateList().split(",")) : null;
    }

    public List<String> getGoodTypes() {
        return StringUtils.isNotBlank(getGoodsType()) ? Arrays.asList(getGoodsType().split(",")) : null;
    }
    
    public Integer getHurryFlagInt() {
        return StringUtils.isNotBlank(getHurryFlag()) ? Integer.parseInt(getHurryFlag()) : null;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTaskStateList() {
        return taskStateList;
    }

    public void setTaskStateList(String taskStateList) {
        this.taskStateList = taskStateList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getHurryFlag() {
        return hurryFlag;
    }

    public void setHurryFlag(String hurryFlag) {
        this.hurryFlag = hurryFlag;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
    
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setTaskStates(List<String> taskStates) {
        this.taskStates = taskStates;
    }

    public void setGoodTypes(List<String> goodTypes) {
        this.goodTypes = goodTypes;
    }

    public Integer getIsPage() {
        return isPage;
    }

    public void setIsPage(Integer isPage) {
        this.isPage = isPage;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getStartIndex() {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        this.startIndex = (pageNo - 1) * getPageSize();
        return this.startIndex;
    }
    
    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1 || pageSize > 20) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageTotal() {
        this.pageTotal = total % pageSize == 0 ? (total / pageSize) : total / pageSize + 1;
        return this.pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }
    
}
