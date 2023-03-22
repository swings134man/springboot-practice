package boot.bootprac.jwt_server.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/************
 * @info : Jwt Server Repository
 * @name : JwtServerRepository
 * @date : 2023/03/22 6:21 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Repository
@RequiredArgsConstructor
@Slf4j
public class JwtServerRepository {

    // in - memory
    private static Map<String, String> map = new HashMap<>();

    // save - RT
    public void saveRefreshToken(String userId, String refreshToken) {
        String put = map.put(userId, refreshToken);
        log.info("save map user(RT) = {}", put);
    }

}
