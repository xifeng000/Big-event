package com.xifeng.bigevent.exception;

import com.xifeng.bigevent.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//参数校验失败异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();//异常信息输出到控制台
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");
        //Spring提供的StringUtils工具类
    }
}
