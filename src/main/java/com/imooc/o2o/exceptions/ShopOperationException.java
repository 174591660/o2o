package com.imooc.o2o.exceptions;

/**
 * Created by zy on 2019/1/12
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 2361446884822298905L;

    public ShopOperationException(String message) {
        super(message);
    }
}
