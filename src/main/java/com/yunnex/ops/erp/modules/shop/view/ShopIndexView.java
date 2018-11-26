package com.yunnex.ops.erp.modules.shop.view;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.BaseView;
import com.yunnex.ops.erp.modules.order.view.OrderSplitInfoView;

/**
 * 商户首页信息视图
 * 
 * @author linqunzhi
 * @date 2018年4月2日
 */
public class ShopIndexView extends BaseView {

    private static final long serialVersionUID = 2253664585341080413L;

    /** 店铺主题门店图片 */
    private String shopPhotoUrl;
    
    /** 商户名称 */
    private String shopName;
    
    /** 商户门店总数 */
    private int storeCount;
    
    /** 商户id */
    private String shopId;

    /** 进行中的服务列表 */
    private List<OrderSplitInfoView> beginList;

    /** 未启动的服务列表 */
    private List<OrderSplitInfoView> noBeginList;

    /** 已完成的服务列表 */
    private List<OrderSplitInfoView> finishList;
    
    /** 是否展示 门店管理 */
    private boolean showStoreManager;

    public String getShopPhotoUrl() {
        return shopPhotoUrl;
    }

    public void setShopPhotoUrl(String shopPhotoUrl) {
        this.shopPhotoUrl = shopPhotoUrl;
    }

    public List<OrderSplitInfoView> getBeginList() {
        return beginList;
    }

    public void setBeginList(List<OrderSplitInfoView> beginList) {
        this.beginList = beginList;
    }


    public List<OrderSplitInfoView> getNoBeginList() {
        return noBeginList;
    }

    public void setNoBeginList(List<OrderSplitInfoView> noBeginList) {
        this.noBeginList = noBeginList;
    }

    public List<OrderSplitInfoView> getFinishList() {
        return finishList;
    }

    public void setFinishList(List<OrderSplitInfoView> finishList) {
        this.finishList = finishList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public boolean isShowStoreManager() {
        return showStoreManager;
    }

    public void setShowStoreManager(boolean showStoreManager) {
        this.showStoreManager = showStoreManager;
    }
    
}
