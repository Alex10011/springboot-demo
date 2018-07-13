package com.alex10011.example.controller;

import com.alex10011.example.enums.RespEnum;
import com.alex10011.example.vo.RspBean;

public class BaseController {
    // 封装返回结果
    protected <T> RspBean<T> success(T data) {
        return new RspBean<T>(data).success();
    }

    // 封装返回结果
    protected <T> RspBean<T> failure(int code) {
        return new RspBean<T>().failure(code);
    }

    // 封装返回结果
    protected <T> RspBean<T> failure(T data) {
        return new RspBean<T>(data).failure(RespEnum.ERROR_BUSINESS_OPERATE.getCode());
    }

    protected <T> RspBean<T> failure(String message) {
        return new RspBean<T>(RespEnum.ERROR_BUSINESS_OPERATE.getCode(), message);
    }
}

/**
 * 如果controller上面注释@Controller， 那么当需要返回json的时候方法上面注释@ResponseBody，否则跳转的是view
 * 如果controller上面注释@RestController ，那么返回直接是json
 */
