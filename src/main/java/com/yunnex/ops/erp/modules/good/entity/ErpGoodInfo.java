package com.yunnex.ops.erp.modules.good.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;
import com.yunnex.ops.erp.modules.good.category.entity.ErpGoodCategory;

/**
 * 商品信息管理Entity
 * 
 * @author Frank
 * @version 2017-10-21
 */
public class ErpGoodInfo extends DataEntity<ErpGoodInfo> {

    private static final long serialVersionUID = 1L;
    private String name; // 商品名称
    private Long price; // 商品价格（单位:分）
    private Integer sort; // 排序字段
    private Long categoryId; // category_id

    private ErpGoodCategory category;

    public ErpGoodCategory getCategory() {
        return category;
    }

    public void setCategory(ErpGoodCategory category) {
        this.category = category;
    }

    public ErpGoodInfo() {
        super();
    }

    public ErpGoodInfo(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 32, message = "商品价格不可为空!")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    @NotNull(message = "排序字段不可为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @NotNull(message = "商品分类编号不能为空")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
