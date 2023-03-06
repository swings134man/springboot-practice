package boot.bootprac.logtracer.v3;

import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_final.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/************
 * @info : Log 추적기 활용을 위한 Service Class - V3(final)
 * @name : OrderServiceV3
 * @date : 2023/03/07 1:56 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 3.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 repository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;

        try{
            status = trace.begin("OrderService.orderItem()");
            repository.save(itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }

}//class
