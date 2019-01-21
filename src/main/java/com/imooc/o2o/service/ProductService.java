package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationExecption;

import java.util.List;

/**
 * Created by zy on 2019/1/18
 */
public interface ProductService {

    /**
     * 增加商品
     *
     * @param product              商品
     * @param thumbnail            商品缩略图片
     * @param productImgHolderList 商品详情图（多个）
     * @return
     * @throws ProductOperationExecption
     */
    ProductExecution addProduct(Product product, ImgHolder thumbnail, List<ImgHolder> productImgHolderList)
            throws ProductOperationExecption;

    /**
     * 查询商品
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);


    /**
     * 修改商品
     *
     * @param product
     * @param imgHolderList
     * @return
     */
    ProductExecution modifyProduct(Product product, ImgHolder thumbnail, List<ImgHolder> imgHolderList)
            throws ProductOperationExecption;

    /**
     * 查询商品列表，并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);


}
