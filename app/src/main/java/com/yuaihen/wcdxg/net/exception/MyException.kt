package com.yuaihen.wcdxg.net.exception

import java.lang.RuntimeException

/**
 * Created by Yuaihen.
 * on 2019/7/8
 * 使用统一的异常处理类
 */
class MyException(val errorCode: Int, val errorMsg: String) : RuntimeException(errorMsg) {


}