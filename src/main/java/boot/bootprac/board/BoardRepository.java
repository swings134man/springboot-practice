package boot.bootprac.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/************
* @info : 게시판 interface
* @name : BoardRepository
* @date : 2022/08/21 9:09 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public interface BoardRepository {

    Board save(Board boardDomain); // 게시글 작성
    Optional<Board> findById(Long id); // 아이디로 게시물 찾기
   // Optional<BoardDomain> findByTitle(); // 제목으로 게시물 찾기
    Page<Board> findAll(Pageable pageable); // 게시물 조회 페이징
//    BoardDomain update(BoardDomain boardDomain); // 게시물 업데이트


} //class
