package boot.bootprac.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BoardControllerTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    void 게시물작성() {
        Board board = new Board();
        board.setTitle("제목1");
        board.setContent("내용1");
        board.setWriter("작성자1");
        //board.setDate();




    }
}