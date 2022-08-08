package boot.bootprac.repository;

import boot.bootprac.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

/************
* @info : Repository의 구현체 class
* @name : MemoryMemberRepository
* @date : 2022/08/05 2:10 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * 설계도(interface)를 기반으로 실제 구현 하는 class
 * Repository란 - DAO 와 같은 역할을 함.
************/
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // DB에 넣는것 대신에 Map 으로 Memory에 일시 저장.
    private static long sequence = 0L; // Key값을 자동으로 생성해주는것.

    @Override
    public Member save(Member member) { // Member(DTO)에 값을 저장
        member.setId(++sequence);
        store.put(member.getId(), member); //store(map)에 저장.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { // 입력 Id 값으로 결과 찾기

        return Optional.ofNullable(store.get(id)); //null 이 반환될 가능성이 있을경우 id가 null 이어도 반환 가능!
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream()     // Lambda
                .filter(member -> member.getName().equals(name)) // Loop돌면서 사용. member의 name이 파라미터의 name이랑 같은지 확인. 같은 경우에만 필터링.
                .findAny(); // 아니더라도 찾으면 그냥 반환해버림. -> 없으면 null 반환됨.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // map의 member들을 반환해줌.
    }


    // 메모리를 클리어해주는 객체.
    public void clearStore() {
        store.clear();
    }
}
