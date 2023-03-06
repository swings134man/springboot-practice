package boot.bootprac.logtracer.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Log 추적기를 위한 Controller class
 * @name : OrderControllerV0
 * @date : 2023/03/06 5:21 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
public class OrderControllerV0 {

    private final OrderServiceV0 serviceV0;

    /**
     * @info    : API 기본 요청
     * @name    : request
     * @date    : 2023/03/06 5:23 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : String itemId
     * @return  : String
     * @Description : itemId가 ex 일시 예외 발생.
     */
    @GetMapping("/v0/request")
    public String request(String itemId) {
        serviceV0.orderItem(itemId);
        return "ok";
    }


}
