package boot.bootprac.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    // 파일 업로드
    public Long saveFile(MultipartFile files) throws IOException {
        if(files.isEmpty()) {
            return null;
        } //if

        // 파일 원본 이름 추출
        String origName = files.getOriginalFilename();

        // 파일이름으로 사용할 uuid
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid + 확장자 결합
        String saveName = uuid + extension;

        // 파일을 불러올 떄 사용할 파일 경로
        String savePath = fileDir + saveName;

        // 파일 entity
        FileEntity file = FileEntity.builder()
                .orgNm(origName)
                .saveNm(saveName)
                .savePath(savePath)
                .build();

        // local에 uuid를 파일명으로 저장
        files.transferTo(new File(savePath));

        // DB 파일정보 저장
        FileEntity saveFile = fileRepository.save(file);

        System.out.println("file  S: " + saveFile);

        return saveFile.getId();
    }// saveFile

    // 파일 다운로드
    public List<FileEntity> viewFile() throws Exception {
        List<FileEntity> filesAll = fileRepository.findAll();

        if(filesAll.isEmpty()) {
            throw new Exception();
        }

        return filesAll;
    }

    // 이미지 출력
    public Optional<FileEntity> showImage(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);

        return fileEntity;
    }

    // 파일 다운
    public FileEntity downFile(Long id) {
        FileEntity file = fileRepository.findById(id).orElse(null);

        return file;
    }

}// class
