package boot.bootprac.common.logtracer.v1;

import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/************
 * @info : Log 추적기를 위한 Repository class V1
 * @name : OrderRepositoryV1
 * @date : 2023/03/06 6:40 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final FirstTraceV1 trace;

    public void save(String itemId) {

        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save()");

            // save Logic
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
