package org.nop.eshop.web.controller;

import org.nop.eshop.web.model.AjaxResult;
import org.nop.eshop.web.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody AjaxResult defaultErrorHandler(HttpServletRequest request, Exception e) {
        return new AjaxResult(new ErrorInfo(request.getRequestURL().toString(), e), false);
    }
}
