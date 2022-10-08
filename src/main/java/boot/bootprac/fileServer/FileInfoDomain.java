package boot.bootprac.fileServer;

import boot.bootprac.board.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/************
* @info : File 정보를 리스트로 불러오기 위한 Entity Class
* @name : FileInfoDomain
* @date : 2022/10/07 7:11 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "file_info")
public class FileInfoDomain extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;
    private String name;
    private String downLoadUrl;
    private String type;
    private Long size;

    // 편의 메서드
    @Builder
    public FileInfoDomain(String name, String downLoadUrl,String type, Long size) {
        this.name = name;
        this.downLoadUrl = downLoadUrl;
        this.type = type;
        this.size = size;
    }

    public FileInfoDomain build(String name, String downLoadUrl,String type, Long size) {
        FileInfoDomain fileInfo = new FileInfoDomain();
        fileInfo.setName(name);
        fileInfo.setDownLoadUrl(downLoadUrl);
        fileInfo.setType(type);
        fileInfo.setSize(size);
        return fileInfo;
    }

}
