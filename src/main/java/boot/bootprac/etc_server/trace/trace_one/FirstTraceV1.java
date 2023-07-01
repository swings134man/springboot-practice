package boot.bootprac.etc_server.trace.trace_one;

import boot.bootprac.etc_server.trace.TraceId;
import boot.bootprac.etc_server.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/************
 * @info : log 출력, 측정 basic class
 * @name : FirstTraceV1
 * @date : 2023/03/06 6:01 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Singleton
 *
 * - 실제 로그를 시작하고 종료할 수 있다. 로그를 출력하고 실행시간도 측정할 수 있다.
 *
 ************/
@Slf4j
@Component
public class FirstTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";


    /**
     * 로그 시작, log Message를 파라미터로 받아서,
     * start log 출력.
     * 응답 결과로 현재 로그상태인 TraceStatus 반환
     * @param message
     * @return TraceStatus
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();

        // log 출력
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }


    // 로그 종료시 호출.
    /**
     * 로그 정상 종료.
     * 파라미터로 시작로그(begin: TraceStatus)를 전달 받음. 이값을 활용해서 실행시간 계산,
     * 종료시 시작할 때와 동일한 로그 메시지 출력.
     * 정상흐름에서 호출.
     * @param status
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }


    // 예외 발생시 호출.
    /**
     * 로그를 예외상황으로 종료함.
     * TraceStatus, Exception 정보를 전달 받아, 실행시간 계산,
     * 종료시 시작할때와 동일한 로그 메시지 출력.
     * 예외 발생시 호출.
     * @param status
     * @param e
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }



    /**
     * complete, end(), exception() 을 처리.
     * 요청 흐름을 한곳에서 처리, 실행시간 측정하고 로그를 남김.
     * @param status
     * @param e
     */
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs(); // 종료시간-시작시간= 걸린시간.
        TraceId traceId = status.getTraceId();
        if(e == null) {
            // 예외가 없을때.
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs );
        } else {
            // 예외 발생시.
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }


    // Depth를 계산해서 Prefix 밑 Level 표시.
    // level이 0
    // level = 1 |-->
    // level = 2 |  |-->

    // level = 1 ex |<x-
    // level = 2 ex |   |<x-
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
