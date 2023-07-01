package boot.bootprac.trace.trace_final;

import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_final.FiledLogTrace;
import org.junit.jupiter.api.Test;

/************
 * @info : 로그 추적기 - 동시성 문제 테스트
 * @name : FiledLogTraceTest
 * @date : 2023/03/07 2:51 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 
 ************/
class FiledLogTraceTest {

    FiledLogTrace trace = new FiledLogTrace();

    @Test
    void begin_end_level2() {
        TraceStatus status1 = trace.begin("status1");
        TraceStatus status2 = trace.begin("status2");
        trace.end(status1);
        trace.end(status2);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus status1 = trace.begin("status1");
        TraceStatus status2 = trace.begin("status2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}