package boot.bootprac.board;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long boardId; //게시판ID PK
    private String title; //게시판 제목
    private String content; // 내용
    private String writer; //작성자
    private String insertDate;
    private String modifiedDate;

    // To Entity

}
