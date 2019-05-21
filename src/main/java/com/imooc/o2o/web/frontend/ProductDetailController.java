package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zy on 2019/1/22
 */
@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            modelMap.put("success", true);
            modelMap.put("product", product);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
}
