package boot.bootprac.board;

import org.springframework.data.jpa.repository.JpaRepository;

/************
* @info : Spring Data Jpa 구현 Repository
* @name : BoardJpaRepository
* @date : 2022/08/21 9:25 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public interface BoardJpaRepository extends JpaRepository<BoardDomain, Long>, BoardRepository {


}//class
