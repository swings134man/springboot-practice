package boot.bootprac.spring_basic.rest.basic.controller;

import boot.bootprac.spring_basic.rest.basic.dto.RestUserResponse;
import boot.bootprac.spring_basic.rest.basic.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/************
 * @info : Spring RestTemplate Controller
 * @name : RestTemplateController
 * @date : 2023/03/12 7:30 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : IntelliJ Project - tenis 프로젝트에 연결됨.
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/rest/")
public class RestTemplateController {

    private final RestTemplateService service;


    /**
     * @info    : Rest Template Get1
     * @name    : get
     * @date    : 2023/03/12 9:41 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : RestTemplate Get - 단순 호출
     */
    @GetMapping("get1")
    public String get() {
        return service.get1();
    }

    @GetMapping("name")
    public String getTest2(@RequestParam String name) {
        log.info("getTest2 호출! param={}", name);
        String name1 = service.getName();
        return "getTest2(name) = " + name1;
    }

    // get3
    @GetMapping("path/{name}")
    public String name(@PathVariable String name) {
        log.info("getTest3 호출! param={}", name);
        String name1 = service.get3Name();
        return "getPathVa = " + name1;
    }

    @PostMapping("user")
    public ResponseEntity<RestUserResponse> getUser() {
        ResponseEntity<RestUserResponse> user = service.getUser();
        return user;
    }

    @PostMapping("addHeder")
    public ResponseEntity<RestUserResponse> addHeader() {
        ResponseEntity<RestUserResponse> result = service.addHeader();
        return result;
    }

    // GetForObject Test
    @GetMapping("get/obj")
    public String getForObject() {
        String res = service.getForObjectTest1();
        return res;
    }

    // getForEntity 전체 내용 Test
    @GetMapping("get/entity")
    public ResponseEntity<String> getForEntityTest() {
        ResponseEntity<String> test = service.get1Test();
        return test;
    }

}
