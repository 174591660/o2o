package com.imooc.o2o.util;

import com.imooc.o2o.dto.ImgHolder;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by zy on 2019/1/10
 */
public class ImageUtil {

    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmss"); // 时间格式化的格式
    private static final Random r = new Random();

    /**
     * 生成图片缩略图
     * 同时返回相对路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImgHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    /**
     * 生成图片的详情图（就是比较大的清晰的图）
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImgs(ImgHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.5f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String getRandomFileName() {
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        int ranNum = r.nextInt(89999) + 10000;
        return sDateFormat.format(new Date()) + ranNum;
    }

    /**
     * 删除文件或目录
     * 如果是文件，则直接删除
     * 如果是目录，则删除目录下的所有文件
     * @param shopImg
     */
    public static void deleteFileOrPath(String shopImg) {
        File file = new File(PathUtil.getImgBasePath() + shopImg);
        if (file.exists()) {
            //如果是目录，则删除目录下的所有文件
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            } else {
                file.delete();
            }
        }
    }
}
