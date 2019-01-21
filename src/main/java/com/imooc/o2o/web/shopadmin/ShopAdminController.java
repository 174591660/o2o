package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zy on 2019/1/13
 *
 * 专门负责页面跳转的controller
 * 其他是具体执行方法返回的controller
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ShopAdminController {

    /**
     *
     * @return
     */
    @RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
    private String shopOperation() {
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
    private String shopManagement() {
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
    private String productCategoryManagement() {
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation", method = RequestMethod.GET)
    private String productOperation() {
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement", method = RequestMethod.GET)
    private String productManagement() {
        return "shop/productmanagement";
    }
}
