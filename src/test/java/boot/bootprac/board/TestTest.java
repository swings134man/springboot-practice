package boot.bootprac.board;

import boot.bootprac.cms.board.Board;
import boot.bootprac.cms.board.BoardDTO;
import boot.bootprac.cms.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TestTest {

    @Autowired
    BoardService service;

    @Test
    void findAll() {
        List<BoardDTO> result = service.findAll();

        Long wantId = 1L;
        Long id = result.get(0).getBoardId();

        assertThat(id).isEqualTo(wantId);

    }

    @Test
    void findOne() {
        Long id = 1L;

        Optional<Board> result = service.findById(id);

        Long rid = result.get().getBoardId();

        assertThat(rid).isEqualTo(id);

    }
}