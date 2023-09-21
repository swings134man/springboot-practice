package boot.bootprac.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/************
 * @info : robots.txt 수집 차단을 막기 위한 컨트롤러
 * @name : RobotController
 * @date : 2023/09/21 9:52 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
public class RobotController {

    @RequestMapping("/robots.txt")
    public void robotBlock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("User-agent: GPTBot \n" +
                                      "User-agent: ChatGpt-User \n" +
                                      "User-agent: CCBot \n" +
                                      "Disallow: /"
                );
    }

}
