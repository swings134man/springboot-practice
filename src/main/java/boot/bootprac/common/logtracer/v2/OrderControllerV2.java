package boot.bootprac.common.logtracer.v2;

import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Log 추적기를 위한 Controller class V2
 * @name : OrderControllerV2
 * @date : 2023/03/06 8:48 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 2.0.0
 * @Description : Log 추적기 적용 - final
 ************/
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 service;
    private final FirstTraceV2 trace;

    /************
     * @info : API 기본 요청 V2
     * @name : OrderControllerV2
     * @date : 2023/03/06 8:50 PM
     * @author : SeokJun Kang(swings134@gmail.com)
     * @version : 2.0.0
     * @Description :
     ************/
    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            service.orderItem(status.getTraceId() ,itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
