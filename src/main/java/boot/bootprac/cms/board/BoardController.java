package boot.bootprac.cms.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

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
public class BoardController {

    private final BoardService boardService;
    private final BoardService s;

    @PostMapping("v1/save")
    @ResponseBody
    public String save(Board in){
//        BoardDomain domain = new BoardDomain();

        log.debug("입력 확인!! : " + in);

        Long result = boardService.save(in);

//        return "boards/board"; // 화면 return
        return String.valueOf(result);
    }

    @GetMapping("/board/v1/retrieveOne")
    public String retrieveOne(Long boardId, Model model) {
        Optional<Board> result = s.findById(boardId);

        model.addAttribute("board", result.get());

        return "boards/findBoard";
    }

    @GetMapping("board/v1/findAll")
    public String retrieveAll(Model model) {

//        List<BoardDTO> result = boardService.findAll();
        List<Board> result = boardService.test();
        System.out.println("DTO : "+ result);

        model.addAttribute("boardList",result);

        return "boards/boardList";
    }



}//class
