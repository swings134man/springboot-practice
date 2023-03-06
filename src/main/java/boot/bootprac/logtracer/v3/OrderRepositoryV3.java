package boot.bootprac.logtracer.v3;

import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_final.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/************
 * @info : Log 추적기 활용을 위한 Repository Class - V3(final)
 * @name : OrderRepositoryV3
 * @date : 2023/03/07 1:55 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 3.0.0
 * @Description :
 ************/
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save()");
            // ------- 저장 logic -------
            if(itemId.equals("ex")) {throw new IllegalStateException("예외 발생");}
            // -------------------------
            sleep(1000);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }


    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }// sleep
}
