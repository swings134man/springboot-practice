package boot.bootprac.etc_server.trace;

/************
 * @info : 로그 추적기의 기본 데이터 파일 -> TraceStatus 로그의 상태정보를 나타내는 클래스
 * @name : TraceStatus
 * @date : 2023/03/06 5:56 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : log의 시작하면, 끝이 필요한데 로그가 종료되었다는것을 표현하기 위한 상태 클래스.
 *
 * - TraceId : 내부에 트랜잭션ID를 포함.
 * - startTimeMs : 로그 시작시간, 로그 종료시 이 시간을 기준으로 시작~종료까지 전체 수행시간 계산.
 * - message : 시작시 사용한 메시지, 이후 로그 종료시에도 해당 메세지를 사용해서 출력.
 ************/
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
