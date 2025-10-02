package boot.bootprac.cms.ck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CkRequestDTO {
    private String title;
    private String writer;
    private String content;
    private MultipartFile thumbnail;
}
