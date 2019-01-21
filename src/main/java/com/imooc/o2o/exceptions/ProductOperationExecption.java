package com.imooc.o2o.exceptions;

/**
 * Created by zy on 2019/1/18
 */
public class ProductOperationExecption extends RuntimeException {

    private static final long serialVersionUID = -6525268234283884273L;

    public ProductOperationExecption(String message) {
        super(message);
    }
}
