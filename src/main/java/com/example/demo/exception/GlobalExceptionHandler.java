package com.example.demo.exception;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.APIResultBody;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.net.BindException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author zhangwenjie
 */
@Slf4j
//@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public APIResultBody bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return APIResultBody.error(e.getErrorCode(), e.getErrorMsg());
    }


    /**
     * 功能描述：数据字段校验
     *
     * @param req
     * @param e
     * @return
     */
   /* @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public APIResultBody validateExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> collect = fieldErrors.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            String defaultMessage = String.join(",", collect);
            return APIResultBody.error("-1", defaultMessage);
        } else {
            return APIResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }*/


  /*  @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public APIResultBody handleBindException(HttpServletRequest req, BindException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        return APIResultBody.error("50090", e.getMessage());
    }*/

    /**
     * 处理数据库保存数据是字段过长
     *
     * @param req
     * @param e
     * @return
     */
   /* @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public APIResultBody mysqlDataTruncationHandler(HttpServletRequest req, DataIntegrityViolationException e) {
        if (e.getCause() instanceof MysqlDataTruncation) {
            log.error("数据库保存字段过长:{}", e.getMessage());
            return APIResultBody.error(CommonEnum.DAT_TRUNCATION);
        }

        if (e.getCause() instanceof SQLException) {
            SQLException sqlException = (SQLException) e.getCause();
            log.error("Database exception info:{}", sqlException.getMessage());
            return APIResultBody.error("603", sqlException.getMessage());
        }
        return APIResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }*/

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public APIResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return APIResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public APIResultBody exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return APIResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}
