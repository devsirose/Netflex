package com.stream.app.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String ERROR_KEY = "errorKey";

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> notFound(Exception ex) {
        log.error("Exception occurs", ex);
        AppFileNotFoundException notFoundException = ((AppFileNotFoundException) ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, StringUtils.hasLength(notFoundException.getMessage())? notFoundException.getMessage(): notFoundException.getErrorKey());
        body.put(ERROR_KEY, notFoundException.getErrorKey());
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler({EpoBadRequestException.class, BudgetExceedException.class})
//    public ResponseEntity<Object> badRequest(Exception ex) {
//        log.error("Exception occurs", ex);
//        Map<String, Object> body = new LinkedHashMap<>();
//        if(ex instanceof EpoBadRequestException) {
//            EpoBadRequestException badRequestException = ((EpoBadRequestException) ex);
//            body.put(MESSAGE, StringUtils.hasLength(badRequestException.getMsg())? badRequestException.getMsg(): badRequestException.getErrorKey());
//            body.put(ERROR_KEY, badRequestException.getErrorKey());
//        }else{
//            BudgetExceedException budgetRequestException = ((BudgetExceedException) ex);
//            body.put(MESSAGE, StringUtils.hasLength(budgetRequestException.getMessage())? budgetRequestException.getMessage(): budgetRequestException.getErrorKey());
//            body.put(ERROR_KEY, budgetRequestException.getErrorKey());
//        }
//        logger.error(ex.getMessage(), ex);
//        return ResponseEntity.badRequest().body(body);
//    }
//
//    @ExceptionHandler({EpoForbidenException.class, AccessDeniedException.class})
//    public ResponseEntity<Object> forbidden(Exception ex) {
//        log.error("Exception occurs", ex);
//        Map<String, Object> body = new LinkedHashMap<>();
//        EpoForbidenException forbidenException = ((EpoForbidenException) ex);
//        String message = LanguageConstant.getMsg(forbidenException.getErrorKey());
//        body.put(MESSAGE, message);
//        body.put(ERROR_KEY, forbidenException.getErrorKey());
//        logger.error(ex.getMessage(), ex);
//        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler({DetailWasUsedException.class})
//    public ResponseEntity<Object> expenseDetailWasUsedHandler(Exception ex) {
//        log.error("Exception occurs", ex);
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put(MESSAGE, ex.getMessage());
//        logger.error(ex.getMessage(), ex);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
//    }
//
//    @ExceptionHandler({EpoIntegrationException.class, NetsuitePaymentTermException.class})
//    public ResponseEntity<Object> integrationError(Exception ex) {
//        log.error("Exception occurs", ex);
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put(DETAIL, ((EpoIntegrationException) ex).getErrorMessage());
//        body.put(MESSAGE, ex.getMessage());
//        logger.error(ex.getMessage(), ex);
//        return ResponseEntity.badRequest().body(body);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerError(Exception ex) {
        log.error("Exception occurs", ex);
        Map<String, Object> body = new LinkedHashMap<>();
        // when user jump fast in video stream, the stream is broken and throw exception
        boolean isBrokenVideoStreamException = (ex instanceof HttpMessageNotWritableException
                && ex.getMessage().contains("'video/mp4'")
        );
        if (isBrokenVideoStreamException) {
            return new ResponseEntity<>(body, HttpStatus.I_AM_A_TEAPOT);
        } else {
            logger.error(ex.getMessage(), ex);
            body.put("MESSAGE", "Please try again later.");
            body.put("TRACE", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("handleExceptionInternal ", ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}

