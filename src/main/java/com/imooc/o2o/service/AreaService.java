package com.imooc.o2o.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imooc.o2o.entity.Area;

import java.io.IOException;
import java.util.List;

/**
 * Created by zy on 2019/1/9
 */
public interface AreaService {

    List<Area> getAreaList() throws IOException;
}
