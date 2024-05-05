package boot.bootprac.etc_server.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.path}")
//    @Value("${servlet.multipart.location}")
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
        String savePath = fileDir +"/"+ saveName;

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

    public void compressZip(List<Long> ids, OutputStream outputStream) {
        // 1. select File List -> DB
        List<FileEntity> listRes = fileRepository.findAllById(ids);

        // 2. Get File And Add File List
        List<File> fileList = new ArrayList<>();
        for (FileEntity f : listRes) {
            fileList.add(new File(f.getSavePath()));
        }


        // 3. Zip File Server Tmp saved
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String now = LocalDateTime.now().format(formatter);
        String zipFileName = fileDir +"/"+ now + ".zip";

        File zipFile = new File(zipFileName);


        // 임시 저장.
        // zip 생성 서버 임시 저장
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))){
            int fileIndex = 0;

            for (File file : fileList) {
                try (FileInputStream in = new FileInputStream(file)) {
                    String attachName = "";
                    for (FileEntity f : listRes) {
                        if(f.getSaveNm().equals(file.getName())) {
                            attachName = f.getOrgNm();
                        }
                    }

                    String uniqueFileName = fileIndex + "_" + attachName;
                    fileIndex++;

                    ZipEntry ze = new ZipEntry(uniqueFileName);
                    out.putNextEntry(ze);

                    int len;
                    byte[] buffer = new byte[8192];

                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }

                    out.closeEntry();
                }catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    out.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        // zip 파일 다운로드
        try (ZipOutputStream out = new ZipOutputStream(outputStream)) {
            File zipServer = new File(zipFileName);
            try (FileInputStream in = new FileInputStream(zipServer)) {
                ZipEntry ze = new ZipEntry(zipServer.getName());
                out.putNextEntry(ze);

                int len;
                byte[] buffer = new byte[8192];

                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }

                out.closeEntry();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            zipFile.delete();
        }


    }

}// class
