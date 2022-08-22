package boot.bootprac.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    void 게시물작성() {
        BoardDomain board = new BoardDomain();
        board.setTitle("제목1");
        board.setContent("내용1");
        board.setWriter("작성자1");
        //board.setDate();




    }
}