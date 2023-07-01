package boot.bootprac.cms.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Test {

    private static BoardService service;

    @GetMapping("board/v1/findAllas")
    public List<BoardDTO> findAll() {

        List<BoardDTO> result = service.findAll();

        return result;
    }

    @GetMapping("/test")
    public List<Board> test() {
        List<Board> result = service.test();
        return result;
    }
}
