package com.ttg.service.park.dto.vo;

import lombok.Data;

/**
 * <p>Title: BaseVo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 13:42
 */
@Data
public class BaseVo<T> {
    private String msg;
    private Integer code;
    private Boolean status;

    private T data;

    public BaseVo() {
    }
    public BaseVo(T t) {
        this.data = t;
    }
}
