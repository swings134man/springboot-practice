package boot.bootprac.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired BoardService service;
    @Autowired BoardRepository repository;



    @Test
    void save() {
        //given
        Board domain = new Board();
        domain.setWriter("worker8");
        domain.setTitle("title8");
        domain.setContent("content8");

        // when
        Long whenResult = service.save(domain);

        // then
        Optional<Board> findId= service.findById(domain.getBoardId());
        Long thenResult= findId.get().getBoardId();

        assertThat(whenResult).isEqualTo(thenResult);

    }

    @Test
    void findById() {

        // given
        Long in = 1L;

        // when
        Optional<Board> result = service.findById(in);

        // then
        assertThat(in).isEqualTo(result.get().getBoardId());

    }

    @Test
    void findAll() {

        List<BoardDTO> all = service.findAll();

        // when
        Long wantId = 1L;
        Long resultId = all.get(0).getBoardId();

        // then
        assertThat(resultId).isEqualTo(wantId);

        // when
        Long wantId2 = 2L;
        Long resultId2 = all.get(1).getBoardId();

        // then
        assertThat(resultId).isEqualTo(wantId);

    }
}