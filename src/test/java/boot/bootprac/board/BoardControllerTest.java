package boot.bootprac.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class BoardControllerTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    void 게시물작성() {
        Board board = new Board();
        board.setTitle("제목1");
        board.setContent("내용1");
        board.setWriter("작성자1");

        Long result = boardService.save(board);
        Long wantId = 3L;

    }

    @Test
    void findOne () {
        Long id = 1L;

        Optional<Board> result = boardService.findById(id);

        Assertions.assertThat(result.get().getBoardId()).isEqualTo(id);
    }




    @Test
    void findAll() {
        System.out.println("asd");

        List<BoardDTO> result = boardService.findAll();

        System.out.println("asd");

        Long wantId = 1L;
        Long boardId = result.get(0).getBoardId();

        Assertions.assertThat(boardId).isEqualTo(wantId);

    }
}