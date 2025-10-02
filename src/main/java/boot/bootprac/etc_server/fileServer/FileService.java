package boot.bootprac.etc_server.fileServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.path}")
//    @Value("${servlet.multipart.location}")
    private String fileDir;

    @Value("${ocr.data.path}")
    private String ocrDataPath;

    private final FileRepository fileRepository;

    // -------------------------------- public methods --------------------------------

    // 파일 업로드
    public Long saveFile(MultipartFile files) throws IOException {
        if(files.isEmpty()) {
            return null;
        } //if

        HashMap<String, String> fileStrings = extractFileStrings(files);

        // 파일 원본 이름 추출
        String origName = fileStrings.get("origName");

        // 파일이름으로 사용할 uuid
        String uuid = fileStrings.get("uuid");

        // 확장자 추출
        String extension = fileStrings.get("extension");

        // uuid + 확장자 결합
        String saveName = fileStrings.get("saveName");

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

    public FileEntity saveFile(MultipartFile files, HashMap<String, String> fileStrings) throws IOException {
        if(files.isEmpty()) {
            return null;
        } //if


        // 파일 원본 이름 추출
        String origName = fileStrings.get("origName");

        // 파일이름으로 사용할 uuid
        String uuid = fileStrings.get("uuid");

        // 확장자 추출
        String extension = fileStrings.get("extension");

        // uuid + 확장자 결합
        String saveName = fileStrings.get("saveName");

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

        return saveFile;
    }// saveFile

    // 임시 파일 저장(CK)
    public FileEntity saveTempFile(MultipartFile files) throws IOException {
        if(files.isEmpty()) {
            return null;
        } //if

        HashMap<String, String> fileStrings = extractFileStrings(files);

        // 파일 원본 이름 추출
        String origName = fileStrings.get("origName");

        // 파일이름으로 사용할 uuid
        String uuid = fileStrings.get("uuid");

        // 확장자 추출
        String extension = fileStrings.get("extension");

        // uuid + 확장자 결합
        String saveName = fileStrings.get("saveName");

        // 파일을 불러올 떄 사용할 파일 경로
        String savePath = fileDir +"/tmp/"+ saveName;

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

        return saveFile;
    }// saveFile


    // 파일 다운로드
    public List<FileEntity> viewFile() throws Exception {
        List<FileEntity> filesAll = fileRepository.findAll();

        if(filesAll.isEmpty()) {
            //TODO: FIXME : Exception 처리 및 null return
            throw new Exception();
        }

        return filesAll;
    }

    // 이미지 출력
    public Optional<FileEntity> showImage(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);

        return fileEntity;
    }

    // 이미지명(UUID) 로 출력(CK)
    public FileEntity showImageByName(String fileName) {
        return fileRepository.findBySaveNm(fileName);
    }

    // 파일 다운
    public FileEntity downFile(Long id) {
        FileEntity file = fileRepository.findById(id).orElse(null);

        return file;
    }

    // 파일 삭제
    public void deleteFile(Long fileId) {
        fileRepository.findById(fileId).ifPresent(file -> {
            File deleteFile = new File(file.getSavePath());
            if(deleteFile.exists()) {
                deleteFile.delete();
            }

            fileRepository.delete(file); //TODO: 임시로 파일 삭제 처리
        });
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

    // OCR 문자열 추출
    public String ocrStringExtract(MultipartFile file) throws IOException {
        HashMap<String, String> fileStrings = extractFileStrings(file);

        String extension = fileStrings.get("extension");

        // Image 확장자일 경우만 OCR 추출
        if(extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png")) {
            log.info("OCR Start");

            FileEntity fileEntity = saveFile(file, fileStrings);// 파일저장
            File savedFile = getFileByPath(fileEntity.getSavePath());

            String resultText = "";

            try {
                ITesseract tesseract = new Tesseract();
                tesseract.setDatapath(ocrDataPath); // Tesseract OCR Data Path(분석 Language Data model)
                tesseract.setLanguage("kor+eng");
                tesseract.setPageSegMode(6); // 6: 단락분석, 3: 완전한 페이지 분석, 1: 자동 레이아웃 감지

                resultText = tesseract.doOCR(savedFile);
            }catch (TesseractException e) {
                resultText = "OCR Error";
                e.printStackTrace();
            }finally {
                // OCR 추출 후 파일 삭제
                deleteFile(fileEntity.getId());
            }

            log.info("OCR Text : {}", resultText);
            return resultText;
        }else {
            return "Not Image File"; //TODO: Exception Process ?
        }
    }

    // -------------------------------- private methods --------------------------------

    // File 에서 원본명, UUID, 확장자, UUID + 확장자 추출
    private HashMap<String, String> extractFileStrings(MultipartFile file) {
        HashMap<String, String> map = new HashMap<>();

        // 파일 원본명 추출
        String origName = file.getOriginalFilename();

        map.put("origName", origName);
        map.put("uuid", UUID.randomUUID().toString());
        map.put("extension", origName.substring(origName.lastIndexOf(".")));
        map.put("saveName", map.get("uuid") + map.get("extension"));

        return map;
    }

    private File getFileByPath(String path) {
        return new File(path);
    }
}// class
