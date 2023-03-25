package boot.bootprac.fileServer;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/************
 * @info : File Or Image Convert 후 REST API Return Test Controller
 * @name : ImageConvertTest
 * @date : 2023/03/25 5:43 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageConvertTest {

    @GetMapping("/test/get/image")
    public ResponseEntity<byte[]> imageReturnRest() throws IOException {
        log.info("Before Image Test");

        byte[] bytes = solution1();
        byte[] bytes1 = solution2();
        byte[] bytes2 = solution3();

        // Byte Image Return
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }


    // 1. Files readAllBytes()
    private byte[] solution1() {
        // File Path
        String filePath = "src/main/resources/static/images/java_logo_icon.png";
        byte[] byteFile = null;

        try {
            byteFile = Files.readAllBytes(new File(filePath).toPath());
        }catch (IOException e){e.printStackTrace();}

        return byteFile;
    }

    // 2. ByteStream toByteArray()
    private byte[] solution2() throws IOException {
        File file = new File("src/main/resources/static/images/java_logo_icon.png");
        byte[] byteFile = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            byteFile = ByteStreams.toByteArray(fileInputStream);
        }catch (IOException e){e.printStackTrace();
        }finally {
            if(fileInputStream != null) {
                fileInputStream.close();
            }
        }// finally

        return byteFile;
    }


    // 3. Image File을 Byte[] 로 얻기.
    private byte[] solution3() throws IOException {
        String filePath = "src/main/resources/static/images/java_logo_icon.png";
        File file = new File(filePath);

        byte[] byteImage = null;

        BufferedImage originalImage = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            originalImage = ImageIO.read(file);
            ImageIO.write(originalImage, "png", out);
            out.flush();
        }catch (IOException e) {e.printStackTrace();
        }finally {
            if(out != null) {out.close();}
        }

        byteImage = out.toByteArray();
        return byteImage;
    }

    // 4. byte Image 다건.
    private byte[][] multiple() throws IOException {
        // TODO : Client 에서 byte[][] 또는 List로 받은 후 Parsing 하는 방법을 사용해야 할 것 같음.
        // File Path
        String filePath = "src/main/resources/static/images"; // 2

//        byte[] byteFiles = Files.readAllBytes(Path.of(filePath)); // 해당 경로의 모든 파일 byte Read
//        return byteFiles;

        byte[][] allImages = new byte[2][];
        Path imageFolder = Paths.get("src/main/resources/static/images");
        allImages[0] = Files.readAllBytes(imageFolder.resolve("chrome.png"));
        allImages[1] = Files.readAllBytes(imageFolder.resolve("java_logo_icon.png"));

        return allImages;
    }

}//class
