package boot.bootprac.trace.trace_one;

import boot.bootprac.trace.TraceStatus;
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
class FirstTraceV2Test {

    @Test
    void begin_end() {
        FirstTraceV2 trace = new FirstTraceV2();
        TraceStatus status1 = trace.begin("hello"); // 시작
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");// 중간

        trace.end(status2); // 나가는것부터
        trace.end(status1);
    }

    @Test
    void begin_exception() {
        FirstTraceV2 trace = new FirstTraceV2();
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}