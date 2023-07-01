package boot.bootprac.cms.member.repository;

import boot.bootprac.cms.member.domain.Member;

import java.util.List;
import java.util.Optional;

/************
* @info : 멤버 repository에 관한 interface
* @name : MemberRepository
* @date : 2022/08/05 2:08 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * 현재 개발 단계에서 DB 선정이 안되었기 때문에
 * 어떤 기능을 구현해야 하는지에 대한 메서드들을 interface로 정의(설계도 역할)
************/
public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
