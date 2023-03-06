package boot.bootprac.trace.trace_final;

import boot.bootprac.trace.TraceId;
import boot.bootprac.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

/************
 * @info : log 출력, 측정 basic class V3
 * @name : FiledLogTrace
 * @date : 2023/03/07 1:38 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 3.0.0
 * @Description : Singleton으로 관리(Bean)는 다른 클래스에서 참조하도록함. -> LogTraceConfig.class
 *
 * -> Thread Local 사용하여 -> 동시성문제 해결.
 ************/
@Slf4j
public class FiledLogTrace implements LogTrace{

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // 이전의 Trace -id, level를 저장. -> 다음 log사용시 활용 가능.
    // Thread Local -> thread마다 각각 저장하여 사용(동시성문제 해결)
    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        Long startTimeMs = System.currentTimeMillis();

        // Trace info
        syncTraceId(); // added
        TraceId traceId = traceIdHolder.get(); //Thread Local get()

        // log 출력
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    // added
    /*
        - feature: 현재 TraceIdHolder가 처음인지 확인
            1. 처음이면 새로운 TraceIdHolder를 생성.
            2. 처음이 아니면 기존의 TraceIdHolder를 참고하여, 다음 HolderId 를 생성하고 저장.

        - 이전에 하나의 Transaction에 대해서 log가 출력된적 있는지 확인 하는것.
            -> TraceId를 필드에 저장하므로, 위의 feature에 따라 기능함.
     */
    private void syncTraceId() {
        // added - thread local
        TraceId traceId = traceIdHolder.get();

        if(traceId == null) {
            traceIdHolder.set(new TraceId()); // Trace Id Create -> thread local.set
        }else {
            traceIdHolder.set(traceId.createNextId());
        }
    }



    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }


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

        releaseTraceId();// added
    }


    // added
    /*
        - traceHolder의 Level을 검사 -> TraceIdHolder가 처음인지 확인.
            1. 처음이면 완료된것으로 간주, TraceHolder를 null로 참조하게함(저장).
            2. 처음이 아니면(null이 아니면), CreatePrevious() 를 이용하여 Level 을 한단계 내린다(-1, 이전으로)

        - TraceHolder의 level 검사 -> 로그 출력 로직을 마치기 위해 사용.
     */
    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();

        if(traceId.isFirstLevel()) { // 0
            traceIdHolder.remove(); //thread local 삭제
        }else {
            traceIdHolder.set(traceId.createPreviousId()); // thread local.set = level -1
        }
    }


    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}//class
