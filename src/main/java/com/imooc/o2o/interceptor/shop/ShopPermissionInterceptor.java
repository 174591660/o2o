package com.imooc.o2o.interceptor.shop;

import com.imooc.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zy on 2019/1/24
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(
                "shopList");
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                if (shop.getShopId().equals(currentShop.getShopId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
