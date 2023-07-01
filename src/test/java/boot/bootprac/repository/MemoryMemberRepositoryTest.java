package boot.bootprac.repository;

import boot.bootprac.cms.member.domain.Member;
import boot.bootprac.cms.member.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/************
* @info : MemberRepository의 기능 작동 확인을 위한 Test Class
* @name : MemoryMemberRepositoryTest
* @date : 2022/08/05 2:26 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * Repository란 - DAO 와 같은 역할.
 * Test시 순서에 의존적인 관계가 되면 안된다.
 * 하나의 테스트가 끝날때마다 저장소나 공용 데이터들을 지워줘야 한다. -> afterEach();
************/
class MemoryMemberRepositoryTest { // Test만 하기때문에 public이 아니어도 됨.

    //MemoryMemberRepository repository = new MemoryMemberRepository(); //Test할 객체.(class)
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() { // Test시 하나의 메서드가 끝날때마다 초기화를 진행. callBack 메서드임. -> Test시 순서에 의존적인 관계가 되면 안됨.

        repository.clearStore(); // 메서드가 하나가 종료되면 map을 clear 해줌.
    }

    @Test
    public void save() { // 저장 Test
        Member member = new Member();
        member.setName("Spring");

        repository.save(member); // Spring이라는 name을 저장후 DTO를 파라메터로 저장 메서드 호출.

        Member result = repository.findById(member.getId()).get(); // 저장 후 제대로 실행되었는지 set했던 name으로 확인. -> Optional Type이기 때문에 마지막에 get()을 사용해서 꺼냄. 실무에선 적합한 방법이 아님.

        //System.out.println("result : " + (result == member)); //확인하는 단순한 방법 -> true/false

        // 테스트 결과물을 계속 Text로 console에서 볼수 없기 때문에 아래의 방법을 사용함.
        // JUnit의 method

        //Assertions.assertEquals(member, result); //왼쪽이 기대하는 값, 오른쪽이 비교할 결과값. -> 기대값과 결과값이 다르면 Test 실패.


        // org.assertj.core.Api -> 문법적으로 훨씬 읽기 쉬움.
        // add-on-demand 'static import'를 사용시 앞의 Assertions.을 없앤 나머지만 사용 가능.

        assertThat(member).isEqualTo(result); // 위의 Assertions와 다른 라이브러리. 해당 라이브러리를 더 많이 사용함.
        //assertThat(member).isEqualTo(null); // 해당 코드는 일부러 error 발생. -> 작동확인을 위함.
    }// save Test

    @Test
    public void findByName() { // 이름으로 값 찾기. -> 해당 name이 DB에 있는지 확인.
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get(); // get() 사용시 optional 을 casting해서 확인 가능. -> Spring2로 Test시 error

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() { // DB에 저장되어있는 데이터의 갯수 확인.
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // 객체가 2개가 저장되어있으면 true/ 아니면 false
    }


}
