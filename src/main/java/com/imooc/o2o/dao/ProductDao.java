package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zy on 2019/1/17
 */
public interface ProductDao {

    /**
     * 新增商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据商品id查询商品
     *
     * @param productId
     * @return
     */
    Product queryByProductId(long productId);

    /**
     * 更新商品
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 分页查询商品列表
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询总数
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品类别（商品的上层）时，需要断掉商品类别和商品之间的联系
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryIdToNull(long productCategoryId);

}
