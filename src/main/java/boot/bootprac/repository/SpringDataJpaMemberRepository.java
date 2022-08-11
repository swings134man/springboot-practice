package boot.bootprac.repository;

import boot.bootprac.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { // T=Member, 2번째는 Entity 식별자 PK의 타입

    // JPQL Select m from Member m where m.name = ?
    // findBy이름 -> 형식으로 만들어놓으면 ID가 아닌 해당 객체로 조회함.
    // findBy이름and아이디 (String name ,String id)-> 형식으로 and, or 연산 가능.
    @Override
    Optional<Member> findByName(String name);

    @Override
    Optional<Member> findById(Long id);

    @Override
    List<Member> findAll();
}
