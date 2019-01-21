package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/20
 */
public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryHeanLineList() {
        List<HeadLine> headLineList = headLineDao.queryHeanLineList(new HeadLine());
        assertEquals(4,headLineList.size());

    }
}