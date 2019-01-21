package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAbatchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("img1");
        productImg1.setImgDesc("img1");
        productImg1.setPriority(100);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(20L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("img1");
        productImg2.setImgDesc("img1");
        productImg2.setPriority(100);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(20L);

        List<ProductImg> list = new ArrayList<>();
        list.add(productImg1);
        list.add(productImg2);

        int effectedNum = productImgDao.batchInsertProductImg(list);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testBqueryProductImgList() {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(4);
        assertEquals(3,productImgList.size());
    }

    @Test
    public void testCdeleteProductImgByProductId() {
        int effectedNum = productImgDao.deleteProductImgByProductId(20);
        assertEquals(2,effectedNum);
    }
}