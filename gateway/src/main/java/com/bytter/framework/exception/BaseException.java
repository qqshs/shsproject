package com.bytter.framework.exception;

import com.bytter.framework.responsException.BaseResponse;
import com.bytter.framework.responsException.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author  shs
 * 异常处理类，捕获全部异常。
 */
@RestControllerAdvice
@Slf4j
public final class  BaseException {
    @ExceptionHandler(Throwable.class)
    public BaseResponse handleError(Throwable e) {
        log.error("Internal Server Error", e);
        return BaseResponse
                .builder()
                .code(ResultCode.INTERNAL_SERVER_ERROR)
                .msg(e.getMessage())
                .build();
    }

    /**
     * 处理业务主动抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse handleError(BusinessException e) {
        log.error("Service Exception", e);
        return BaseResponse
                .builder()
                .code(e.getResultCode())
                .msg(e.getMessage())
                .build();
    }

}
