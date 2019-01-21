package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by zy on 2019/1/12
 */
public class ShopServiceImplTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(43L);
        shop.setShopName("修改后的" + shop.getShopName());
        File shopImg = new File("F:\\photo\\image\\test.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImgHolder thumbnail = new ImgHolder(shopImg.getName(), is);
        ShopExecution se = shopService.modifyShop(shop, thumbnail);
        System.out.println(se.getShop().getShopName());
        System.out.println(se.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws IOException {
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
        shop.setPhone("111222");
        shop.setPriority(10);
        shop.setShopAddr("test");
        shop.setShopDesc("test");
        shop.setShopImg("test");
        shop.setShopName("同福客栈二店");
        File shopImg = new File("F:\\photo\\image\\test.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImgHolder thumbnail = new ImgHolder(shopImg.getName(), is);
        ShopExecution shopExecution = shopService.addShop(shop, thumbnail);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());

    }


    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(10L);
        shopCondition.setShopCategory(shopCategory);
        Area area = new Area();
        area.setAreaId(3L);
        shopCondition.setArea(area);
        ShopExecution se = shopService.getShopList(shopCondition, 2, 3);

        System.out.println(se.getShopList().size());
        System.out.println(se.getCount());
    }
}