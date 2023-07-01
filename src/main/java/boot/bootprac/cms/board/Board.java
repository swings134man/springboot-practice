package boot.bootprac.cms.board;

import boot.bootprac.common.config.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

/************
* @info : 게시판 domain class
* @name : BoardDomain
* @date : 2022/08/21 9:06 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOARD")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardid")
    private Long boardId; //게시판ID PK

    @Column(name = "title")
    private String title; //게시판 제목

    @Column(name = "content")
    private String content; // 내용

    @Column(name = "writer")
    private String writer; //작성자
//
//    @Column(name = "insertdate", updatable = false)
//    private String insertDate; // 작성 시간
//    @Column(name = "modifieddate")
//    private String modifiedDate; // 변경 시간


} //class
