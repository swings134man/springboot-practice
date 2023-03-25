package boot.bootprac.qr.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/************
 * @info : QR Code 생성 및 제공 Controller
 * @name : QrController
 * @date : 2023/03/24 5:32 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class QrController {


    @GetMapping("/qr/tistory")
    public ResponseEntity<byte[]> qrToTistory() throws WriterException, IOException {
        int width = 200;
        int height = 200;
        String url = "https://lucas-owner.tistory.com/";

        // QR Code - BitMatrix: qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        // QR Code - Image 생성. : 1회성으로 생성해야 하기 때문에, stream으로 Generate(1회성이 아니면 File로 작성 가능.)
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(); //output Stream
            MatrixToImageWriter.writeToStream(encode, "PNG", out); //Bitmatrix, file.format, outputStream
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());

        }catch (Exception e){
            log.warn("QR Code OutputStream 도중 Excpetion 발생, {}", e.getMessage());
        }//catch

        return null;
    }// QR : Tistory Link



    @GetMapping("/qr/image")
    public ResponseEntity<byte[]> qrToImage() throws WriterException, IOException{
        String text = "http://localhost:8080/qr/get/image";

        // QR
        int width = 200;
        int height = 200;

        BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(encode, "PNG", outputStream);

            return
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(outputStream.toByteArray());
        }catch (Exception e){
            log.warn("QR Code Exceptions {}", e.getMessage());
        }
        return null;
    }

    // Image File Response
    @GetMapping("/qr/get/image")
    public ResponseEntity<byte[]> name() throws IOException {
        // Image name - ImageFile to Byte[]
        String imageName = "java_logo_icon.png";
        byte[] byteFile = getImageBye(imageName);

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");
        headers.add("Content-Length", String.valueOf(byteFile.length));

        // Return
        return new ResponseEntity<byte[]>(byteFile, headers, HttpStatus.OK);
    }

        // Image to Byte[]
        private byte[] getImageBye(String imageName) throws IOException{
            // image 경로 및 File
            ClassPathResource classPathResource = new ClassPathResource("static/images/"+ imageName);
            File file = classPathResource.getFile();

            byte[] byteImage = null;

            BufferedImage originalImage = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            originalImage = ImageIO.read(file);
            ImageIO.write(originalImage, "png", out);
            out.flush();

            byteImage = out.toByteArray();
            return byteImage;
        }


}
