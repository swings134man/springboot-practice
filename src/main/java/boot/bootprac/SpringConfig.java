package boot.bootprac;

import boot.bootprac.board.BoardRepository;
import boot.bootprac.board.BoardService;
import boot.bootprac.repository.*;
import boot.bootprac.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // JDBC Template 사용 .
//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // JPA 사용
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    // Spring data JPA
    private final MemberRepository memberRepository;
    //private final BoardRepository boardRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
       // this.boardRepository = boardRepository;
    }

    @Bean
    public MemberService memberService() {
        // Service는 repository를 갖고있으므로 넣어주지 않으면 에러 발생.
        return new MemberService(memberRepository);
    }


   // @Bean
    //public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
       // return new JdbcTemplateMemberRepository(dataSource); //Jdbc Template
        //return new JpaMemberRepository(em); // Jpa
    //}

}
