package boot.bootprac.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
}
