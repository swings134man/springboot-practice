package boot.bootprac.service;

import boot.bootprac.domain.Member;
import boot.bootprac.repository.MemberRepository;
import boot.bootprac.repository.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/************
* @info : 회원도메인 관련 Service
* @name : MemberService
* @date : 2022/08/05 4:39 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

/*
// 아래의 repository 생성자는 Lombok의 Required(final이 붙는 필드 값만 파라미터로 받는 생성자 생성) 사용.
// 서비스 입장에서는 repository를 외부에서 넣어주는 상황임. -> DI (Dependency Injection)

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
*/

    /**
    * @info    : 회원가입.
    * @name    : join
    * @date    : 2022/08/05 4:40 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  : Member.getId() -> Long
    */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 x
        validateDuplicateMember(member); // 중복 회원 검증
        /*
            findByName의 반환 타입은 Optional이기 때문에 변수로 받지 않아도
            위와 같이 Optional 메서드를 사용하여 코드를 줄일수 있음.
            ifPresent() -> 이미 해당값이 존재한다면 -> 다음동작
         */

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증 Method
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });// 값이 있으면? (존재하는 이름이면?) -> Optional 안에 Member객체가 있는것임.
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    /**
     * 하나의 회원만 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
