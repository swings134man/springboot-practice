package boot.bootprac.logtracer.v1;

import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_one.FirstTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/************
 * @info : Log 추적기를 위한 Service class V1
 * @name : OrderServiceV1
 * @date : 2023/03/06 6:41 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
public class OrderServiceV1 {
    private final OrderRepositoryV1 repository;
    private final FirstTraceV1 trace;

    public void orderItem(String itemId) { // to save

        TraceStatus status = null;

        try {
            status = trace.begin("OrderService.orderItem()");
            repository.save(itemId);
            trace.end(status);

        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }// orderItem
}
