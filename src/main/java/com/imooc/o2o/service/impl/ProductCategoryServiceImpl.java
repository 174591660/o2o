package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zy on 2019/1/15
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryByShopId(long shopId) {
        return productCategoryDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if (productCategoryList == null || productCategoryList.size() <= 0) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
        try {
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺类别创建失败");
            }
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        } catch (Exception e) {
            throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
        }

    }

    /**
     * 删除商品类别的时候，要把商品和商品类别的联系断掉，即把tb_product表的product_category_id改成null
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException {
        try {
            int updatedNum = productDao.updateProductCategoryIdToNull(productCategoryId);
            if (updatedNum <= 0) {
                throw new ProductCategoryOperationException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺类别删除失败");
            }
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }

    }
}
