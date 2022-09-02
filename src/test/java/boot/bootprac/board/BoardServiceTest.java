package boot.bootprac.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        domain.setWriter("worker3");
        domain.setTitle("title3");
        domain.setContent("content3");

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
}