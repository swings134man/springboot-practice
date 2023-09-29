package boot.bootprac.common.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/************
 * @info : DB 접속정보 암복화 테스트 Class
 * @name : JasyptConfigTest
 * @date : 2023/09/30 12:05 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
class JasyptConfigTest {

    @Test
    void jasyptRun() {

        // TARGET
        String url = "jdbc:mysql://localhost:3306/boot_jpa?useSSL=false";
        String username = "root";
        String password = "1234";

        // ENCRYPT
        String encryptUrl = encrypt(url);
        String encryptUsername = encrypt(username);
        String encryptPassword = encrypt(password);

        // DECRYPT
        System.out.println("encryptUrl = " + encryptUrl);
        System.out.println("encryptUsername = " + encryptUsername);
        System.out.println("encryptPassword = " + encryptPassword);

        // RESULT
        Assertions.assertThat(url).isEqualTo(decrypt(encryptUrl)); //기존 URL 과, 암호화 후 복호화한 URL 비교
    }

    // 암호화
    private String encrypt(String input) {
        String key = "ThisIsPassWord12!@"; // yml

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setSaltGenerator(new StringFixedSaltGenerator("bootprac")); // 지정한 특정 salt 값으로만 암/복호화 가능

        return encryptor.encrypt(input);
    }

    // 복호화
    private String decrypt(String input) {
        String key = "ThisIsPassWord12!@"; // yml

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setSaltGenerator(new StringFixedSaltGenerator("bootprac"));

        return encryptor.decrypt(input);
    }
}