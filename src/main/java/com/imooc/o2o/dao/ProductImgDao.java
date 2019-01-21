package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

/**
 * Created by zy on 2019/1/17
 */
public interface ProductImgDao {

    /**
     * 批量新增商品详情图（大，细节的图）
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 根据商品id查询相关的详情图
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 根据商品id删除详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

}
