package boot.bootprac.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/************
* @info : 게시판 컨트롤러
* @name : BoardController
* @date : 2022/08/21 9:25 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("board/")
public class BoardController {

    private static BoardService boardService;

    @PostMapping("v1/save")
    public String save(Board in){
//        BoardDomain domain = new BoardDomain();

        log.debug("입력 확인!! : " + in);

        Long result = boardService.save(in);

        return "boards/boardList"; // 화면 return
    }

    @GetMapping("v1/retrieveOne")
    public String retrieveOne(Long boardId) {
        boardService.findById(boardId);

        return "";
    }


}//class
