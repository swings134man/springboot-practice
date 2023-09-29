package boot.bootprac.common.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/************
 * @info : Application Property 암호화 Config Class
 * @name : JasyptConfig
 * @date : 2023/09/29 11:41 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : WARNING! : 운영 YML 파일에는 암호화 값만 추가 할것. - 알고리즘 및 salt 값은 application.yml에 추가하지 말것.
 *
 * 1. 패스워드, 알고리즘의 경우 서버 실행시 환경변수 설정 혹은 2번 방법 참조
 * 2. Git Secret 사용 혹은 3번 사용.
 * 3. 해당 값들을 사용하는 파일을 .gitignore 로 관리 후 운영 yml 에는 암호화 값만 적용.
 ************/
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${encrypt.password}")
    private String PASSWORD;

    @Value("${encrypt.algorithm}")
    private String ALGORITHM;
    private static final String KEY_OBTENTION_ITERATIONS = "1000";
    private static final String STRING_OUTPUT_TYPE = "base64";
    private static final String POOL_SIZE = "1";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor(); // 암호화 환경 설정
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD); // 암호화 키 - 서버 환경변수 값 사용
        config.setAlgorithm(ALGORITHM); // 암호화 알고리즘
        config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS); //  해싱 반복 횟수
        config.setSaltGenerator(new StringFixedSaltGenerator("bootprac")); //salt 미지정 시 랜덤 생성(RandomSaltGenerator)
        config.setStringOutputType(STRING_OUTPUT_TYPE); // 인코딩 타입
        config.setPoolSize(POOL_SIZE); // 암호화 객체를 가지고 있을 pool size

        encryptor.setConfig(config);
        return encryptor;
    }
}
