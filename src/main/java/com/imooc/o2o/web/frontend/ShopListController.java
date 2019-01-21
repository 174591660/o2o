package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2019/1/21
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 返回商品列表页里的shopCategory列表（一级或二级），以及区域信息列表
     * 根据带有parentId来区别：存在parentId是查询该商品类别id下的所有二级类别，不存在是查询所有一级类别
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //存在parentId，那么找到这个parentId下所有的商品类别
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parentCategory);

                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            //不存在parentId
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //判断页码是否有效
        if ((pageIndex > -1) && (pageSize > -1)) {
            //试着获取一级商品类别id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //试着获取二级商品类别id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //试着获取区域信息
            long areaId = HttpServletRequestUtil.getLong(request, "areaId");
            //试着获取店铺名称
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, long areaId, String shopName) {
        Shop shop = new Shop();
        if (parentId != -1) {
            //查询某个一级店铺类别下，所有二级店铺类别关联的店铺列表
            ShopCategory parent = new ShopCategory();
            ShopCategory child = new ShopCategory();
            parent.setShopCategoryId(parentId);
            child.setParent(parent);
            shop.setShopCategory(child);
        }
        if (shopCategoryId != -1) {
            //查询某个二级店铺类别下的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shop.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shop.setArea(area);
        }
        if (shopName != null) {
            shop.setShopName(shopName);
        }
        shop.setEnableStatus(1);
        return shop;
    }

}
