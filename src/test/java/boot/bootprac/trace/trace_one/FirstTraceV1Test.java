package boot.bootprac.trace.trace_one;

import boot.bootprac.etc_server.trace.TraceStatus;
import boot.bootprac.etc_server.trace.trace_one.FirstTraceV1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/************
 * @info : FirstTraceV1 Class의 작동을 Test
 * @name : FirstTraceV1Test
 * @date : 2023/03/06 6:26 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : log 출력, 측정하는 클래스를 테스트 진행.
 ************/
@SpringBootTest
class FirstTraceV1Test {

    @Test
    void begin_end() {
        FirstTraceV1 trace = new FirstTraceV1();
        TraceStatus status = trace.begin("hello"); // 시작
        trace.end(status);
    }

    @Test
    void begin_exception() {
        FirstTraceV1 trace = new FirstTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}