package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

/**
 * Created by zy on 2019/1/12
 */
public interface ShopService {

    /**
     * 新增店铺
     *
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImgHolder thumbnail) throws ShopOperationException;

    /**
     * 修改店铺
     *
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImgHolder thumbnail) throws ShopOperationException;

    /**
     * 通过id查询shop
     *
     * @param shopId
     * @return
     */
    Shop getShopById(long shopId);

    /**
     * 获得符合条件的店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return  由于ShopExecution类中，正好有shopList属性和count属性，完全符合接口返回类型，因此使用该类作为返回类型
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);


}
