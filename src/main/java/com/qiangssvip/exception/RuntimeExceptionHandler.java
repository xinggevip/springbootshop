package com.qiangssvip.exception;

import com.qiangssvip.enums.ResponseEnum;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)  // 要处理的异常类型
    @ResponseBody
    public ResponseVo handle(RuntimeException e){
        return ResponseVo.error(ResponseEnum.ERROR,e.getMessage());
    }
}
