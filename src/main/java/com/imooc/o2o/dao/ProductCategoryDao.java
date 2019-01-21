package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zy on 2019/1/15
 */
public interface ProductCategoryDao {

    /**
     * 通过shopId查询商品类别
     * 一对多，查出来应该是集合
     * @param shopId
     * @return
     */
    List<ProductCategory> queryByShopId(long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 根据productCategoryId和shopId删除商品类别
     * 两个参数，需要指定唯一标识
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);

}
