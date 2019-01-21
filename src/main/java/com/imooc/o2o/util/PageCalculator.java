package com.imooc.o2o.util;

/**
 * Created by zy on 2019/1/15
 */
public class PageCalculator {

    /**
     * 由于前端页面是第几页，每页有多少条记录的形式展示的
     * 因此需要转换成sql里面的limit
     *
     * 第一页：limit  0，pageSize
     * 第二页：limit  pageSize，pageSize
     * 第三页：limit  2*pageSize，pageSize
     * 第四页：limit  3*pageSize，pageSize
     * @param pageIndex     第几页
     * @param pageSize      每页有多少条记录
     * @return              sql语句limit的rowIndex
     */
    public static int calculateRowIndex(int pageIndex,int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }

}
