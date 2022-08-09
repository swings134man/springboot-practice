package boot.bootprac.service;

import boot.bootprac.domain.Member;
import boot.bootprac.repository.MemberRepository;
import boot.bootprac.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/************
* @info : 통합테스트
* @name : MemberServiceTest
* @date : 2022/08/05 5:03 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * 1. 서버 + DB 연결까지 Test
 * 2. Transactional : DB에 commit 을 하지 않음. -> rollBack, Test완료 후에.
 * 3. @SpringBootTest : 스프링 컨테이너와 함께 Test 진행.
************/
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    //@Commit
    void 회원가입() {
        // Test 시에 @Commit 어노테이션 생성시 -> DB에 반영됨. (rollback 되지 않음.)
        // given
        Member member = new Member();
        member.setName("spring_jpa2");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get(); //Id 로 회원 하나 조회
        assertThat(member.getName()).isEqualTo(findMember.getName());  // Test 입력용 name과, 실제 결과의 name 이 같은지 확인.
    }

    @Test
    public void 중복_회원_예외() {
        // Test는 정상작동도 중요하지만 예외상황이 훨씬 중요함.

        //given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// Test 시 Exception 확인. 해당 클래스익셉션이 나와야함, 오른쪽의 해당 메서드가. (Lambda 사용) Excpetion시 true 반환

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 에러 메시지가 제대로 됬는지 확인.

    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}