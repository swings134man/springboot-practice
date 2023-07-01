package boot.bootprac.common.logtracer.v2;

import boot.bootprac.etc_server.trace.TraceId;
import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/************
 * @info : Log 추적기를 위한 Service class V2 - final
 * @name : OrderServiceV2
 * @date : 2023/03/06 8:49 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 2.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 repository;
    private final FirstTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) { // to save

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            repository.save(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }// orderItem
}
