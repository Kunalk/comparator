package com.waes.techinterview.comparator.exceptionhandler;

import com.waes.techinterview.comparator.exception.ErrorCodeEnum;
import com.waes.techinterview.comparator.exception.ProcessingException;
import com.waes.techinterview.comparator.exception.ValidationException;
import com.waes.techinterview.comparator.service.validator.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.ws.Response;

/**
 * Created by Kunal on 08-11-2018.
 */
@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public final ResponseEntity<String> handleValidationException(ValidationException ex, WebRequest request){
        if(ex.getErrorCode() == ErrorCodeEnum.DATA_NOT_PRESENT){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } else  if(ex.getErrorCode() == ErrorCodeEnum.DATA_INVALID){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else{
            return new ResponseEntity<String>("System could not process your request at this time", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ProcessingException.class)
    public final ResponseEntity<String> handleProcessingException(ProcessingException ex, WebRequest request){
        if(ex.getErrorCode() == ErrorCodeEnum.DATA_NOT_PRESENT){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } else  if(ex.getErrorCode() == ErrorCodeEnum.RETRIEVE_ERROR || ex.getErrorCode() == ErrorCodeEnum.PERSIST_ERROR){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else{
            return new ResponseEntity<String>("System could not process your request at this time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
