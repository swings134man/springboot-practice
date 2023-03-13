package boot.bootprac.spring_basic.rest.kakao.controller;

import boot.bootprac.spring_basic.rest.kakao.service.TransKakaoService;
import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_final.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Kakao Open API - Translate by Rest template Controller
 * @name : TransKakaoController
 * @date : 2023/03/13 4:07 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class TransKakaoController {

    private final TransKakaoService service;
    private final LogTrace trace;

    @GetMapping("/api/kakao/translate")
    public String translate(@RequestParam String lang, @RequestParam String text) {

        TraceStatus status = null;

        status = trace.begin("TransKakaoController.translate()");
        String translate = service.translate(lang, text);
        trace.end(status);

        return translate;
    }

}
