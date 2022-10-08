package boot.bootprac.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

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

}
