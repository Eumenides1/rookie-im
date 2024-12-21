package com.rookie.stack.im.common.exception.handler;

import com.rookie.stack.im.common.exception.AppIdMissingException;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.CommonErrorEnum;
import com.rookie.stack.im.domain.vo.resp.base.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Name：GlobalExceptionHandler
 * Author：eumenides
 * Created on: 2024/12/17
 * Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * validation参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x -> errorMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
        String message = errorMsg.toString();
        log.info("validation parameters error！The reason is:{}", message);
        return ApiResult.fail(CommonErrorEnum.PARAM_VALID.getErrorCode(), message.substring(0, message.length() - 1));
    }

    /**
     * 自定义校验异常（如参数校验等）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult businessExceptionHandler(BusinessException e) {
        log.info("business exception！The reason is：{}", e.getMessage(), e);
        return ApiResult.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 处理空指针的异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    public ApiResult exceptionHandler(NullPointerException e) {
        log.error("null point exception！The reason is: ", e);
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

    /**
     * 未知异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ApiResult systemExceptionHandler(Exception e) {
        log.error("system exception！The reason is：{}", e.getMessage(), e);
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

    /**
     * 未携带 AppID 异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AppIdMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleAppIdMissingException(AppIdMissingException ex) {
        log.error("method miss AppId {}", ex.getMessage());
        return ApiResult.fail(CommonErrorEnum.MISS_APPID);
    }


}
