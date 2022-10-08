package boot.bootprac.fileServer;

import boot.bootprac.board.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "file")
public class FileEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String orgNm; // 파일 원본명
    private String saveNm;
    private String savePath;

    // 편의 메서드
    @Builder
    public FileEntity(Long id, String orgNm, String saveNm, String savePath) {
        this.id = id;
        this.orgNm = orgNm;
        this.saveNm = saveNm;
        this.savePath = savePath;
    }
}
