package com.ksyun.start.camp.rest;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应DTO
 *
 * @author libo14
 **/
@Getter
@NoArgsConstructor
public class RestResult<T> implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private int code;

    /**
     * 异常信息
     */
    private String msg;

    private String descr;


    /**
     * 返回结果
     */
    private T data;

    public T getData() {
        return data;
    }

    /**
     * 链式方法
     */
    public RestResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public RestResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public RestResult<T> descr(String descr) {
        this.descr = descr;
        return this;
    }

    public RestResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public static RestResult success() {
        RestResult dto = new RestResult();
        dto.code(RestConsts.DEFAULT_SUCCESS_CODE).msg(RestConsts.SUCCESS_MESSAGE);
        return dto;
    }

    public static RestResult success(Object data, String descr) {
        RestResult dto = new RestResult();
        dto.code(RestConsts.DEFAULT_SUCCESS_CODE).msg(RestConsts.SUCCESS_MESSAGE).data(data).descr(descr);
        return dto;
    }

    public static RestResult failure() {
        RestResult dto = new RestResult();
        dto.code(500).msg(RestConsts.ERROR_MESSAGE);
        return dto;
    }
}
