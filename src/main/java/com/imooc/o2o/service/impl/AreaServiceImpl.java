package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2019/1/9
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    private static String AREALISTKEY = "arealist";

    @Override
    public List<Area> getAreaList() throws IOException {
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {
            areaList = areaDao.queryArea();
            String jsonString = mapper.writeValueAsString(areaList);
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            areaList = mapper.readValue(jsonString, javaType);
        }
        return areaList;
    }
}
