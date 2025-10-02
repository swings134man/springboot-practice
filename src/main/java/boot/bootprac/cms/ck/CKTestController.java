package boot.bootprac.cms.ck;

import boot.bootprac.etc_server.fileServer.FileEntity;
import boot.bootprac.etc_server.fileServer.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @package : boot.bootprac.spring_basic.rest.basic.controller
 * @name : CKTestController.java
 * @date : 2025. 10. 2. 오전 3:50
 * @author : lucaskang(swings134man)
 * @Description: 여러가지 multifile, dto Request, response 테스트
**/
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ck")
public class CKTestController {

    private final FileService fileService;

    @GetMapping("/upload")
    public String getPage() {
        return "/ck/upload";
    }


    // multipart(썸네일) + dto 업로드
    @PostMapping("/save")
    public ResponseEntity<Object> uploadBoard(@ModelAttribute CkRequestDTO dto) {
        log.info(String.valueOf(dto));
        /*
            TODO: DB 저장 로직 필요함
            content 내부의 <img> 를 추출 -> 파일명 기반으로 가져옴 (tmp)
            -> tmp -> original 폴더 이관 및 DB 업데이트 필요 (카테고리 별 폴더로 이관)
            -> 또한 <img> 태그에 업데이트된 파일명으로 변경 필요
         */
        return ResponseEntity.ok().build();
    }


    // Ck editor 이미지 업로드(Temp)
    // JSON 형태로 url, uploaded 를 필수로 반환해야함.
    @PostMapping("/img/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
        FileEntity fileResult = fileService.saveTempFile(file);
        log.info("upload file : " + file.getOriginalFilename());

        // 게시글 저장할 때 content 안의 <img src="...">를 파싱(추출 후 실제 폴더로 이동 시키는 작업 필요)해서 저장
        Map<String, Object> response = new HashMap<>();
        response.put("url", "/files/name/" + fileResult.getSaveNm());
        response.put("uploaded", true);
        return ResponseEntity.ok(response);
    }

}
