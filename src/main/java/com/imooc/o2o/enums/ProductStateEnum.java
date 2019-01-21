package com.imooc.o2o.enums;

/**
 * Created by zy on 2019/1/18
 */
public enum ProductStateEnum {

    SUCCESS(1, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1"), EMPTY(-1003,"商品为空");

    private int state;

    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum stateOf(int index) {
        for (ProductStateEnum state : ProductStateEnum.values()) {
            if (index == state.getState()) {
                return state;
            }
        }
        return null;
    }

}
