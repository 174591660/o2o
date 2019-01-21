package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/15
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBqueryByShopId() {
        List<ProductCategory> productCategoryList = productCategoryDao.queryByShopId(43L);
        assertEquals(2,productCategoryList.size());
    }

    @Test
    public void testABatchInsertProductCategory() {
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryName("店铺类别1");
        pc1.setProductCategoryDesc("test1");
        pc1.setPriority(100);
        pc1.setCreateTime(new Date());
        pc1.setShopId(43L);
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryName("店铺类别2");
        pc2.setProductCategoryDesc("test2");
        pc2.setPriority(90);
        pc2.setCreateTime(new Date());
        pc2.setShopId(43L);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(pc1);
        productCategoryList.add(pc2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testCDeleteProductCategory() {
        long shopId = 43L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryByShopId(shopId);
        for (ProductCategory pc : productCategoryList) {
            if ("店铺类别1".equals(pc.getProductCategoryName()) || "店铺类别2".equals(pc.getProductCategoryName())) {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1,effectedNum);
            }
        }
    }
}