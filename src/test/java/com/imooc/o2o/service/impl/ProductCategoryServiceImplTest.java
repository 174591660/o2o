package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.service.ProductCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/15
 */
public class ProductCategoryServiceImplTest extends BaseTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void getProductCategoryByShopId() {
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryByShopId(20L);
        assertEquals(5,productCategoryList.size());

    }
}