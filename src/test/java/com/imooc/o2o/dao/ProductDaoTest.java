package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/17
 */
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testUpdateProductCategoryIdToNull() {
        int i = productDao.updateProductCategoryIdToNull(9L);
//        assertEquals(4, i);
    }

    @Test
    public void testQueryProductList() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(20L);
        product.setShop(shop);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);
        product.setProductCategory(productCategory);
//        product.setProductName("奶茶");
        product.setEnableStatus(1);

        List<Product> productList = productDao.queryProductList(product, 3, 3);
        assertEquals(3, productList.size());
    }

    @Test
    public void testQueryProductCount() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(20L);
        product.setShop(shop);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);
        product.setProductCategory(productCategory);
        product.setProductName("奶茶");
        product.setEnableStatus(1);

        int count = productDao.queryProductCount(product);
        assertEquals(2, count);
    }

    @Test
    public void testQueryByProductId() {
        Product product = productDao.queryByProductId(21);
        System.out.println(product);
//        assertEquals(3,product.getProductImgList().size());

    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(21L);
        Shop shop = new Shop();
        shop.setShopId(15L);
        product.setShop(shop);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(20L);
        product.setProductCategory(productCategory);
        product.setLastEditTime(new Date());

        int i = productDao.updateProduct(product);
        assertEquals(1, i);
    }

    @Test
    public void insertProduct() {
        Product product = new Product();
        product.setProductName("test1");
        product.setProductDesc("test1");
        product.setImgAddr("test1");
        product.setNormalPrice("123");
        product.setPriority(100);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setPoint(1);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductCategory(productCategory);
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);

        int e1 = productDao.insertProduct(product);
        assertEquals(1, e1);

        Product product1 = new Product();
        product1.setProductName("test2");
        product1.setProductDesc("test2");
        product1.setImgAddr("test2");
        product1.setNormalPrice("323");
        product1.setPriority(100);
        product1.setCreateTime(new Date());
        product1.setEnableStatus(1);
        product1.setPoint(1);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryId(1L);
        product1.setProductCategory(productCategory1);
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        product1.setShop(shop);

        int e2 = productDao.insertProduct(product);
        assertEquals(1, e2);
    }
}