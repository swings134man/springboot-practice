package boot.bootprac.board;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/************
* @info : Spring Data Jpa 구현 Repository
* @name : BoardJpaRepository
* @date : 2022/08/21 9:25 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public interface BoardJpaRepository extends JpaRepository<Board, Long>, BoardRepository {

    @Override
    Board save(Board boardDomain);

    @Override
    Optional<Board> findById(Long boardId);


}//class
