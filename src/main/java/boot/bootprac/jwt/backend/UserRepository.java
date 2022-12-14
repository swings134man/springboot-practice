package boot.bootprac.jwt.backend;

import boot.bootprac.jwt.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @EntityGraph(attributePaths = "authorities") // 쿼리 수행시 Lazy조회가 아니고 Eager조회로 authorities정보를 같이 가져온다.
    Optional<User> findOneWithAuthoritiesByUsername(String username); // 유저정보와 권한정보 같이 가져옴.
}
