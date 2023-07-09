package boot.bootprac.exceptions;

import boot.bootprac.cms.member.domain.Member;
import boot.bootprac.cms.member.service.MemberService;
import boot.bootprac.common.exception.ExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/************
 * @info : Custome Exception test
 * @name : MemberExceptionTest
 * @date : 2023/07/09 2:51 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@SpringBootTest
@Transactional
public class MemberExceptionTest {

    @Autowired
    MemberService service;

    @Test
    void 존재_여부 () {
        // given
        Member member = new Member();
        member.setName("test1");

        // then
        Assertions.assertThrows(ExistException.class, () -> {
            service.join(member);
        });
    }

}
