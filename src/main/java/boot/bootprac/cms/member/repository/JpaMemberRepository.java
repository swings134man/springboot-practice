package boot.bootprac.cms.member.repository;

import boot.bootprac.cms.member.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // save from jpa
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 조회할 type , PK(식별자)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JPQL 사용. -> 단건이 아닌 경우 JPQL을 사용해서 해야함 -> PK기반이 아니기 때문이다.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny(); // Optional로 받기 때문에 stream().findany() 사용 해야함.
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("SELECT m from Member m", Member.class).getResultList(); //Member entity -> 객체를 대상으로 Query를 생성함. (Table 대상이 아님.) -> Member 엔티티 자체를 Select 조회함. *을 안씀.
        return result;
    }
}