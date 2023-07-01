package boot.bootprac.etc_server.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 페이지 return
    @GetMapping("/files/fileMain")
    public String filePage() {
        return "/files/fileMain";
    }

    // 파일 업로드
    @PostMapping("/file/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files) throws IOException {


        System.out.println("file : " + file);
        System.out.println("files : " + files);

            fileService.saveFile(file);

            for(MultipartFile multipartFile : files){
                fileService.saveFile(multipartFile);
            }

        return "redirect:/";
    }

    // ---------- 파일 다운 화면 ------------------
    @GetMapping("/files/fileView")
    public String viewPage(Model model) throws Exception{

        List<FileEntity> fileView = fileService.viewFile();
        model.addAttribute("all", fileView);

        return "/files/fileView";
    }

    // 이미지 출력
    @GetMapping("/files/{fileId}")
    @ResponseBody // Uri resource 로 파일 reading 후 이미지 바이너리 반환
    public Resource showImage(@PathVariable("fileId") Long id, Model model) throws IOException{

        Optional<FileEntity> fileEntity = fileService.showImage(id);

        return new UrlResource("file:" + fileEntity.get().getSavePath());
    }

    // 파일 다운로드
    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downFile(@PathVariable Long id) throws MalformedURLException {

        FileEntity fileEntity = fileService.downFile(id);

        // URL Path
        UrlResource resource = new UrlResource("file:" + fileEntity.getSavePath());

        // 원본이름, UTF8확장
        String encodedFileName = UriUtils.encode(fileEntity.getOrgNm(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자 출력하는 헤더 설정
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.
                ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

}//class
