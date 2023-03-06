package boot.bootprac.trace;

import java.util.UUID;

/************
 * @info : 로그 추적기의 기본 데이터 파일 -> Trace Id
 * @name : TraceId
 * @date : 2023/03/06 5:35 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Transaction ID, depth(깊이) Level 을 표시.
 *
 * - TraceId 클래스는 : id, level 정보만 가지고 있다.
 *
 * - ID : UUID 를 통해서 생성.
 ************/
public class TraceId {

    private String id;
    private int level;

    // 기본 생성자.
    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    /**
     * 내부에서만 사용하는 생성자 -> 다음Id를 생성
     */
    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * UUID 생성 Method
     * @return String UUID
     */
    private String createId() {
        // 앞 8자리 : ab9916f, full UUID = ab9916f-3cde-4d24-8241-256102c203a2
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * depth 를 표현하기 위한 메서드 -> id는 같지만, level 증가. - 다음 단계표현.
     * @return
     */
    public TraceId createNextId() {
        return new TraceId(id, level+1);
    }

    /**
     * depth를 표현하기위한 메섣, -> 이전 level
     * @return
     */
    public TraceId createPreviousId() {
        return new TraceId(id, level-1);
    }

    /**
     * 첫번째 Level 표현 메서드
     * @return
     */
    public boolean isFirstLevel() {
        return level == 0;
    }

    // Getter
    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
