package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationExecption;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2019/1/18
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 这里做了这么几件事：
     * 1.添加商品的缩略图文件
     * 2.添加商品记录
     * 3.添加商品的详情图文件（批量）
     * 4.添加商品详情图记录（批量）
     *
     * @param product              商品
     * @param thumbnail            商品缩略图片
     * @param productImgHolderList 商品详情图（多个）
     * @return
     * @throws ProductOperationExecption
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImgHolder thumbnail, List<ImgHolder> productImgHolderList)
            throws ProductOperationExecption {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //赋值默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);

            //添加缩略图文件
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            //添加记录到数据库
            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationExecption("商品添加失败");
                }
            } catch (Exception e) {
                return new ProductExecution(ProductStateEnum.INNER_ERROR);
            }
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryByProductId(productId);
    }

    /**
     * 修改商品：
     * 1.先看看有没有图片，有则删除所有该商品的图片（缩略图和详情图），在把新图片加上
     * 有图片的时候：1.删除图片文件；2.要把详情图表的记录也一起删掉
     * 2.更新商品记录
     *
     * @param product
     * @param imgHolderList
     * @return
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImgHolder thumbnail, List<ImgHolder> imgHolderList)
            throws ProductOperationExecption {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            try {
                //看看有没有图
                if (thumbnail != null) {
                    Product tempProduct = productDao.queryByProductId(product.getProductId());
                    if (tempProduct.getImgAddr() != null) {
                        ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                    }
                    addThumbnail(product, thumbnail);
                }
                if (imgHolderList != null && imgHolderList.size() > 0) {
                    deleteProductImgs(product.getProductId());
                    addProductImgList(product, imgHolderList);
                }
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationExecption("更新商品失败");
                }
                //更新成功，从数据库查出来在返回
                product = productDao.queryByProductId(product.getProductId());
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationExecption("modifyProduct error:" + e.getMessage());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 查询类的业务，不需要加上事务
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        if (productList != null) {
            pe.setProductList(productList);
            pe.setCount(count);
        } else {
            pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
        }
        return pe;
    }

    /**
     * 删除详情图和记录
     *
     * @param productId
     */
    private void deleteProductImgs(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //删除图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库记录
        productImgDao.deleteProductImgByProductId(productId);
    }

    private void addProductImgList(Product product, List<ImgHolder> productImgHolderList) throws ProductOperationExecption {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImgHolder productImgImgHolder : productImgHolderList) {
            String addr = ImageUtil.generateNormalImgs(productImgImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(addr);
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationExecption("创建商品详情图失败");
                }
            } catch (Exception e) {
                throw new ProductOperationExecption("addProductImgList error:" + e.getMessage());
            }
        }
    }

    /**
     * 这个函数干了两件事
     * 1.生成缩略图
     * 2.返回相对路径，并且赋值给product对象
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImgHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
