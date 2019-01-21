package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/9
 */
public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(10L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println(shopList.size());
        System.out.println(count);


    }

    @Test
    public void testQueryShopById() {
        long shopId = 15L;
        Shop shop = shopDao.queryShopById(shopId);
        System.out.println(shop.getShopCategory().getShopCategoryName());
        System.out.println(shop.getArea().getAreaName());
    }

    @Test
    public void insertShop() {
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        Shop shop = new Shop();
        personInfo.setUserId(8L);
        area.setAreaId(3L);
        shopCategory.setShopCategoryId(10L);
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopCategoryId(10L);
        shop.setAdvice("审核中");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setLastEditTime(new Date());
        shop.setLatitude(1.1D);
        shop.setLongitude(1.2D);
        shop.setPhone("111222");
        shop.setPriority(10);
        shop.setShopAddr("test");
        shop.setShopDesc("test");
        shop.setShopImg("test");
        shop.setShopName("test");
        int i = shopDao.insertShop(shop);
        assertEquals(1, i);
    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(29L);
        shop.setShopAddr("测试地址");
        shop.setShopDesc("测试描述");
        shop.setLastEditTime(new Date());
        int i = shopDao.updateShop(shop);
        assertEquals(1, i);
    }

    @Test
    public void testQueryShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shopCondition.setOwner(owner);
        Area area = new Area();
        area.setAreaId(3L);
        shopCondition.setArea(area);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(10L);
        shopCondition.setShopCategory(shopCategory);

        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 3);
        System.out.println(shopList.size());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println(count);
    }


}