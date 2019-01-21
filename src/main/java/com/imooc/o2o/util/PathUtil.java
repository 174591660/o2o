package com.imooc.o2o.util;

/**
 * Created by zy on 2019/1/10
 */
public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/projectdev/image/";
        } else {
            basePath = "/home/zy/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        StringBuilder shopImagePathBuilder = new StringBuilder();
        shopImagePathBuilder.append("/upload/images/item/shop/");
        shopImagePathBuilder.append(shopId);
        shopImagePathBuilder.append("/");
        String shopImagePath = shopImagePathBuilder.toString().replace("/",
                separator);
        return shopImagePath;
    }
}
