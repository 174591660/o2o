package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zy on 2019/1/9
 */
public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("东苑",areaList.get(0).getAreaName());
    }
}