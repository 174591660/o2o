package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zy on 2019/1/19
 */
public class ProductServiceImplTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductId(21L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(200L);
        product.setProductCategory(productCategory);
        Shop shop = new Shop();
        shop.setShopId(15L);
        product.setShop(shop);
        //缩略图
        File productImg = new File("F:\\photo\\image\\1.jpg");
        InputStream is = new FileInputStream(productImg);
        ImgHolder thumbnail = new ImgHolder(productImg.getName(), is);
        //详情图
        File detail = new File("F:\\photo\\image\\2.jpg");
        InputStream is1 = new FileInputStream(detail);
        ImgHolder detailImg = new ImgHolder(productImg.getName(), is1);

        List<ImgHolder> imgHolderList = new ArrayList<>();
        imgHolderList.add(detailImg);

        ProductExecution pe = productService.modifyProduct(product, thumbnail, imgHolderList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());


    }

    @Test
    public void addProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductName("测试商品1");
        product.setProductDesc("描述1");
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setPriority(100);
        product.setPoint(100);

        //缩略图
        File productImg = new File("F:\\photo\\image\\1.jpg");
        InputStream is = new FileInputStream(productImg);
        ImgHolder thumbnail = new ImgHolder(productImg.getName(), is);
        //详情图
        File detail = new File("F:\\photo\\image\\2.jpg");
        InputStream is1 = new FileInputStream(detail);
        ImgHolder detailImg = new ImgHolder(productImg.getName(), is1);
        List<ImgHolder> imgHolderList = new ArrayList<>();
        imgHolderList.add(detailImg);

        ProductExecution pe = productService.addProduct(product, thumbnail, imgHolderList);
        assertEquals(1, pe.getState());

    }
}