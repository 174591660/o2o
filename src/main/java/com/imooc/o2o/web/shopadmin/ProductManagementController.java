package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImgHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2019/1/19
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private final static int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //看看上传的文件是否为空
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        //这个是缩略图文件，是要传入到service接口的参数
        ImgHolder thumbnail = null;
        //这个是详情图片，也是要传入到service接口的参数
        List<ImgHolder> productImgHolderList = new ArrayList<>();
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgHolderList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传文件不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //数据都获取完了，判断一下是否有效然后准备入库
        if (product != null && productImgHolderList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

                ProductExecution pe = productService.addProduct(product, thumbnail, productImgHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 这个方法，需要返回商品的类别（商品的上层），供前端填充类别列表下拉框
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            try {
                Product product = productService.getProductById(productId);
                List<ProductCategory> productCategoryList = productCategoryService.
                        getProductCategoryByShopId(product.getShop().getShopId());
                modelMap.put("product", product);
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品id为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //加了个直接在商品列表点击下架的出口
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //看看上传的文件是否为空
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        //这个是缩略图文件，是要传入到service接口的参数
        ImgHolder thumbnail = null;
        //这个是详情图片，也是要传入到service接口的参数
        List<ImgHolder> productImgHolderList = new ArrayList<>();
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgHolderList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //数据都获取完了，判断一下是否有效然后准备更新数据
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 重构出来的方法
     * @param request
     * @param productImgHolderList
     * @return
     * @throws IOException
     */
    private ImgHolder handleImage(MultipartHttpServletRequest request, List<ImgHolder> productImgHolderList) throws IOException {
        ImgHolder thumbnail;
        MultipartHttpServletRequest multipartRequest = null;
        multipartRequest = request;
        CommonsMultipartFile thumbnailFile = null;
        thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail = new ImgHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        //取出详情图并构建 List<ImgHolder> productImgHolderList列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            MultipartFile productImgFile = multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                ImgHolder productImgHolder = new ImgHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgHolderList.add(productImgHolder);
            }
        }
        return thumbnail;
    }


    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1) && currentShop != null && currentShop.getShopId() != null) {
            //获取其他的一些条件
            String productName = HttpServletRequestUtil.getString(request, "productName");
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            long shopId = currentShop.getShopId();
            Product product = compactProductCondition(shopId, productName, productCategoryId);

            ProductExecution pe = productService.getProductList(product, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition(long shopId, String productName, long productCategoryId) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);

        if (productName != null) {
            productCondition.setProductName(productName);
        }
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        return productCondition;
    }

}
