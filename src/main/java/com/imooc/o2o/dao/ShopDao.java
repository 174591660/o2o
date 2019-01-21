package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zy on 2019/1/9
 */
public interface ShopDao {

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    Shop queryShopById(long shopId);

    /**
     * 分页查询店铺，可以输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域id，owner
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
