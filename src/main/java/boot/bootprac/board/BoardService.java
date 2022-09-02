package boot.bootprac.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository repository;

//    @Autowired
//    BoardRepository repository;

    // save method
    public Long save(Board in){
        BaseTimeEntity time = new BaseTimeEntity();

        // Date 반환타입 - String
//        in.setInsertDate(time.getInsertDate());
//        in.setModifiedDate(time.getModifiedDate());


        repository.save(in);

        return in.getBoardId();
    } //save

    // 조회 한건
    public Optional<Board> findById(Long boardId) {
        return repository.findById(boardId);
    }


}
