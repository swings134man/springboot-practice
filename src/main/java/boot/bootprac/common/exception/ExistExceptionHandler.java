package boot.bootprac.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/************
 * @info : Exist Exception Handler - 중복 데이터 save 불가시, DB 조회 후 존재시 발생하는 Exception Handler
 * @name : ExistExceptionHandler
 * @date : 2023/07/09 2:44 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Slf4j
@RestControllerAdvice
public class ExistExceptionHandler {

    @ExceptionHandler(ExistException.class)
    public ExistException handleException(ExistException e, HttpServletRequest request) {
        log.error("ExistException : {}", e.getMessage());
        return e;
    }

}
