package boot.bootprac.common.logtracer.v1;

import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/************
 * @info : Log 추적기를 위한 Service class V1
 * @name : OrderControllerV1
 * @date : 2023/03/06 6:42 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Log 추적기 적용
 ************/
@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 service;
    private final FirstTraceV1 trace;

    /************
     * @info : API 기본 요청 V1
     * @name : OrderControllerV1
     * @date : 2023/03/06 6:42 PM
     * @author : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @Description :
     ************/
    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;

        try {
            // log 시작 -> 컨트롤러명 + 메서드명
            status = trace.begin("OrderController.request()");
            // 비지니스 로직
            service.orderItem(itemId);
            // log 종료
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져주어야 함. -> 위에서 잡아채기때문에 어플리케이션의 흐름이 변경됨.
            // trace.exception() 후 정상흐름으로 진행된다.
        }

    }
}
