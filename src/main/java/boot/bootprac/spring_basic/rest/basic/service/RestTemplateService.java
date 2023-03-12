package boot.bootprac.spring_basic.rest.basic.service;

import boot.bootprac.spring_basic.rest.basic.dto.RestUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/************
 * @info : Spring RestTemplate Service
 * @name : RestTemplateService
 * @date : 2023/03/12 7:32 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class RestTemplateService {


    // get
    public RestUserResponse getRest() {
        // 다른 서버에 요청하기위한 Data Set
        // URI를 동적으로 생성하여, url, 파라미터 값을 지정하거나 변경하는것이 편하다.
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080") // Target Url
                .path("/api/open/get")
                .queryParam("id", "swings134")
                .queryParam("name" , "kang")
                .encode()
                .build()
                .toUri();

        System.out.println("완성된 url : " + uri.toString());

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<RestUserResponse> forEntityResult = restTemplate.getForEntity(uri, RestUserResponse.class); // 결과.
        return forEntityResult.getBody();
    }

    public void name() {
    }
}
