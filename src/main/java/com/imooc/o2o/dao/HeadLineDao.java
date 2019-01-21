package com.imooc.o2o.dao;

import com.imooc.o2o.entity.HeadLine;

import java.util.List;

/**
 * Created by zy on 2019/1/20
 */
public interface HeadLineDao {

    List<HeadLine> queryHeanLineList(HeadLine headLine);
}
