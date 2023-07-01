package boot.bootprac.common.logtracer.v2;

import boot.bootprac.etc_server.trace.TraceId;
import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/************
 * @info : Log 추적기를 위한 Repository class V2
 * @name : OrderRepositoryV2
 * @date : 2023/03/06 8:49 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 2.0.0
 * @Description :
 ************/
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final FirstTraceV2 trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId ,"OrderRepository.save()");
            if(itemId.equals("ex")) { // itemId=ex인 경우 예외 발생
                throw new IllegalStateException("에외 발생.");
            }
            sleep(1000);

            trace.end(status);

        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }//save

    /**
     * Thread 사용, millis 초간 일시정지.
     * @param millis
     */
    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }// sleep
}
