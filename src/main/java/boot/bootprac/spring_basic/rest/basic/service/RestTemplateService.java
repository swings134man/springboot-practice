package boot.bootprac.spring_basic.rest.basic.service;

import boot.bootprac.spring_basic.rest.basic.dto.RestUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
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
 *
 *  - Header 추가, 핸들링할때는 -> exchange() 사용.
 *  - getForObject ->
 *  - getForEntity -> 상태코드 및 내가 받는 데이터 타입을 정확히 명시됨 -> 파악하기 쉽다. -> 타겟 서버에서 보내주는 타입을 정확히 모르는경우 사용해도됨. -> String Type 으로 return.
 *      -> Header 및 상세 정보를 얻을 수 있음. -> 추천하는 return 타입임.
 *
 *  1. post
 *      1. post - Request Entity 사용시, Http Body -> object -> object mapper -> json -> rest template -> http body json으로 전송.
 *      2.
 *
 *  TODO : getForObject 관련 데이터 확인. generic Response 추가
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
    } //test 용

    // get1 - 파라미터 없음.
    public String get1() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/get1")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        log.info("return 값 상태코드 : {}", forEntity.getStatusCode());
        log.info("return body : {}", forEntity.getBody());
        return forEntity.getBody();
    }//get1


    // get2 - param=name(String)
    public String getName() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/name")
                .queryParam("name", "king")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        log.info("return 값 상태코드 : {}", forEntity.getStatusCode());
        log.info("return body : {}", forEntity.getBody());
        return forEntity.getBody();
    }//get2()


    // get3 - Path {} -> name
    public String get3Name() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/path/{name}")
                .encode()
                .build()
                .expand("kingking3") // 복수의 값을 넣어야 할경우 , 을 추가하여 구분함.
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        log.info("return 값 상태코드 : {}", forEntity.getStatusCode());
        log.info("return body : {}", forEntity.getBody());

        return forEntity.getBody();
    }//get3()


    // post1 - getUser
    public ResponseEntity<RestUserResponse> getUser() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user")
                .queryParam("id", "test1")
                .queryParam("name", "test11")
                .encode()
                .build()
                .toUri();

        RestUserResponse dto = new RestUserResponse();
        dto.setId("dto1Id");
        dto.setName("dto1Name");

        RestTemplate restTemplate = new RestTemplate();

        // 내부적으로, uri, dto(RequestBody로 인식) 필요함
        ResponseEntity<RestUserResponse> result = restTemplate.postForEntity(uri, dto, RestUserResponse.class);

        log.info("return 값 상태코드 : {}", result.getStatusCode());
        log.info("return body : {}", result.getBody());

        return result;
    }// post1 - getUser


    // post - addHeader
    public ResponseEntity<RestUserResponse> addHeader() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/addHeader")
                .encode()
                .build()
                .toUri();

        RestUserResponse dto = new RestUserResponse();
        dto.setId("dto2Id");
        dto.setName("dto2Name");

        // 요청 - Request Entity
        RequestEntity<RestUserResponse> requestEntity = RequestEntity
                .post(uri)
                .header("server-header", "HeaderName") //Header key= Header Name
                .body(dto);

        RestTemplate restTemplate = new RestTemplate();

        // Header를 설정을 해줄때는 exchange를 사용. - Request Entity 에서 header,body,uri를 설정해 주었음.
        ResponseEntity<RestUserResponse> exchange = restTemplate.exchange(requestEntity, RestUserResponse.class);

        log.info("return 값 상태코드 : {}", exchange.getStatusCode());
        log.info("return body : {}", exchange.getBody());

        return exchange;
    }







}//class