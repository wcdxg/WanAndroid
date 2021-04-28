package com.yuaihen.wcdxg.net.exception;

/**
 * Created by Yuaihen.
 * on 2019/7/8
 * 处理异常
 * getMessage() 获取异常信息
 */
public class MyException extends RuntimeException {

//    public MyException() {
//    }

    public MyException(String message) {
        super(message);
    }


}
