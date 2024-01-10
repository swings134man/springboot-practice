package boot.bootprac.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : 고의적인 Error 발생을 위한 클래스
 * @name : ErrorReturn
 * @date : 1/10/24 10:01 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequestMapping("/api/error")
@Slf4j
public class ErrorReturn {



    @GetMapping("/bad")
    public ResponseEntity<String> badReturn(@RequestParam String param) {

        // 고의적인 Exception
        if(param.equals("go")) {
            log.debug("RunTimeException !@!!!! param = {}", param);
            throw new RuntimeException("day");
        }

        String msg = "success";
        return new ResponseEntity<>(msg ,HttpStatus.OK);
    }


}
