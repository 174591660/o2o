package com.imooc.o2o.dto;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * Created by zy on 2019/1/16
 */
public class ProductCategoryExecution {

    private int state;//结果状态

    private String stateInfo;//状态信息

    private List<ProductCategory> productCategoryList;//ProductCategory列表（结果数据）

    public ProductCategoryExecution() {

    }

    /**
     * 操作失败使用的构造器，无结果数据
     * @param stateEnum
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 操作成功使用的构造器，有结果数据
     * @param stateEnum
     * @param productCategoryList
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
