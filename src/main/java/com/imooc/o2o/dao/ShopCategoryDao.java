package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zy on 2019/1/13
 */
public interface ShopCategoryDao {

    /**
     * 当传入的shopCategoryCondition为null时，需要把parent_id为null的一级店铺类别获取出来
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
