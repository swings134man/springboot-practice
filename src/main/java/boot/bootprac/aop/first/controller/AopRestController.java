package boot.bootprac.aop.first.controller;

import boot.bootprac.aop.first.UserVO;
import boot.bootprac.aop.first.annotation.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/************
 * @info : AOP Test Rest Controller Class
 * @name : AopRestController
 * @date : 2023/03/16 5:42 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/aop/")
public class AopRestController {

    /**
     * Get Test : PathVariable (NO Param)
     * @param name
     * @return String
     */
    @GetMapping("get/{name}")
    public String getTest(@PathVariable String name) {
        return name;
    }

    /**
     * get Test2 -> Param
     * @param id
     * @return String
     */
    @GetMapping("v2/get")
    public String getTest2(@RequestParam String id) {
        return "Return Id = " + id;
    }

    /**
     * AOP TEST : POST -> Body(UserVO)
     * @param vo
     * @return UserVO
     * @throws InterruptedException
     */
    @Timer
    @PostMapping("v1/post")
    public UserVO postTest1(@RequestBody UserVO vo) throws InterruptedException {
//        log.info("request VO = {} ", vo); // MethodCheckAOP.class 에서 입력 Param, return Value Check 후 Log 출력
        Thread.sleep(3000); // 3초
        UserVO res = new UserVO();
        res.setId(vo.getId());
        res.setName(vo.getName());
        res.setPhone(vo.getPhone());

        return res;
    }

    @PostMapping("v2/post")
    public UserVO postTest2(@RequestBody UserVO vo) throws InterruptedException {
//        log.info("request VO = {} ", vo); // MethodCheckAOP.class 에서 입력 Param, return Value Check 후 Log 출력
//        UserVO res = new UserVO();
//        res.setId(vo.getId());
//        res.setName(vo.getName());
//        res.setPhone(vo.getPhone());
        return vo;
    }

    @PostMapping("v1/ex")
    public String postThrowTest(@RequestParam String msg) {
        if(msg.equals("ex")) {
            throw new RuntimeException("Parameter is not allowed");
        }
        return msg;
    }

}
