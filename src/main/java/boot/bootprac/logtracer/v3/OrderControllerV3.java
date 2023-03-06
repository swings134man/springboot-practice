package boot.bootprac.logtracer.v3;

import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_final.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Log 추적기 활용을 위한 Controller Class - V3(final)
 * @name : OrderControllerV3
 * @date : 2023/03/07 1:54 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 3.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 service;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;

        try{
            status = trace.begin("OrderController.requst()");
            service.orderItem(itemId);
            trace.end(status);

            return "ok";
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }
}
