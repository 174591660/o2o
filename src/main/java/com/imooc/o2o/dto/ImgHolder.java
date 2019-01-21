package com.imooc.o2o.dto;

import java.io.InputStream;

/**
 * Created by zy on 2019/1/18
 *
 * 封装图片的类，包含名字和字节输入流
 */
public class ImgHolder {

    private String imageName;

    private InputStream image;

    public ImgHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
