package boot.bootprac.service;

import boot.bootprac.domain.Member;
import boot.bootprac.repository.MemberRepository;
import boot.bootprac.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/************
* @info : Member Service Test Code
* @name : MemberServiceTest
* @date : 2022/08/05 5:03 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
class MemberServiceTest {

    //MemberService memberService = new MemberService(); //테스트 객체
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository(); // 메서드 한개실행후 clear 를 하기위에 해당 로직이 있는 객체 생성

    // repository객체를 위와같이 생성하면 같은 인스턴스가 아니기떄문에 Test시 확실하지 않음 그래서 Service 내의 repository를 아래와 같이 넣어줌.
    MemberService memberService;
    MemoryMemberRepository memberRepository;


    @BeforeEach
    public void beforeEach() { // 동작하기 전에 실행
        memberRepository = new MemoryMemberRepository(); // Test시작전 repository를 필드 변수에 넣고
        memberService = new MemberService(memberRepository); // 그변수를 서비스의 파라미터가 있는 생성자로 넣어줌.

    }

    @AfterEach
    public void afterEach() { // clear
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring1");

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

        // try/catch 사용 애매하기 때문에 위의 문법 사용.
       /* try {
            memberService.join(member2); // 똑같은 이름이 두번 들어가니까 Exception 발생.
            fail();
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.222");
        }*/


        //then

    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}