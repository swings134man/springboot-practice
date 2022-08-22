package boot.bootprac.board;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/************
* @info : 게시판 domain class
* @name : BoardDomain
* @date : 2022/08/21 9:06 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Entity
@Data
public class BoardDomain extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardId; //게시판ID PK
    private String title; //게시판 제목
    private String content; // 내용
    private String writer; //작성자
    private LocalDateTime insertDate; // 작성 시간
    private LocalDateTime modifiedDate; // 변경 시간

} //class
