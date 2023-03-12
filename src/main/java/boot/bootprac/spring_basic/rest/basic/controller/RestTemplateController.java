package boot.bootprac.spring_basic.rest.basic.controller;

import boot.bootprac.spring_basic.rest.basic.dto.RestUserResponse;
import boot.bootprac.spring_basic.rest.basic.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Spring RestTemplate Controller
 * @name : RestTemplateController
 * @date : 2023/03/12 7:30 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class RestTemplateController {

    private final RestTemplateService service;


    /**
     * @info    : Rest Template Get
     * @name    : get
     * @date    : 2023/03/12 9:41 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : RestTemplate Get
     */
    @GetMapping("/rest/get")
    public RestUserResponse get() {
        return service.getRest();
    }


}
