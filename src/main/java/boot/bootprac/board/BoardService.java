package boot.bootprac.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository repository;
    private final BoardJpaRepository Jrepository;
    private final ModelMapper modelMapper; // DTO <-> Entity

//    @Autowired
//    BoardRepository repository;

    // save method
    public Long save(Board in){
        BaseTimeEntity time = new BaseTimeEntity();

        // Date 반환타입 - String
//        in.setInsertDate(time.getInsertDate());
//        in.setModifiedDate(time.getModifiedDate());


        Jrepository.save(in);

        return in.getBoardId();
    } //save

    // 조회 한건
    public Optional<Board> findById(Long boardId) {
        return repository.findById(boardId);
    }

    // 전체 조회
    public List<BoardDTO> findAll()  {

//         List<BoardDTO> result = repository.findAll().stream()
//                            .map(board -> modelMapper.map(board, BoardDTO.class))
//                            .collect(Collectors.toList());


        List<BoardDTO> result= Jrepository.findAll().stream()
                                .map(board -> modelMapper.map(board, BoardDTO.class))
                                .collect(Collectors.toList());

            System.out.println("DTO : "+ result);



         return result;
    }

    // 전체 조회 테스트
    public List<Board> test () {
        List<Board> result = Jrepository.findAll();

        return result;
    }

}
