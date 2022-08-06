package boot.bootprac;

import boot.bootprac.repository.MemberRepository;
import boot.bootprac.repository.MemoryMemberRepository;
import boot.bootprac.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/************
* @info : Java Code로 직접 Bean 등록하는 방법. class
* @name : SpringConfig
* @date : 2022/08/05 6:12 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/


@Configuration
public class SpringConfig {

    /*@Bean
    public MemberService memberService() {
        // Service는 repository를 갖고있으므로 넣어주지 않으면 에러 발생.
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }*/

}
