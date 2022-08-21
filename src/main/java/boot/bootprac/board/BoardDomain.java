package boot.bootprac.board;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/************
* @info : 게시판 domain class
* @name : BoardDomain
* @date : 2022/08/21 9:06 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Entity
@Data
public class BoardDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardId; //게시판ID PK
    private String title; //게시판 제목
    private String content; // 내용
    private String writer; //작성자
    private String date; // 작성시간


} //class
