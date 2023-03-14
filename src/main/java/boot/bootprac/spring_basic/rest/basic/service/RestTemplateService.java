package boot.bootprac.spring_basic.rest.basic.service;

import boot.bootprac.config.RestTemplateConfig;
import boot.bootprac.spring_basic.rest.basic.dto.ResGeneric;
import boot.bootprac.spring_basic.rest.basic.dto.RestUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/************
 * @info : Spring RestTemplate Service
 * @name : RestTemplateService
 * @date : 2023/03/12 7:32 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 *
 *  - RestTemplate 은 Bean 으로 생성하여 사용하거나, 최소 클래스당 한개를 생성하여 사용하는것이 좋은 방법이다. -> 리소스를 낭비할 필요가 없음.
 *
 *  - Header 추가, 핸들링할때는 -> exchange() 사용.
 *  - getForObject ->
 *  - getForEntity -> 상태코드 및 내가 받는 데이터 타입을 정확히 명시됨 -> 파악하기 쉽다. -> 타겟 서버에서 보내주는 타입을 정확히 모르는경우 사용해도됨. -> String Type 으로 return.
 *      -> Header 및 상세 정보를 얻을 수 있음. -> 추천하는 return 타입임.
 *
 *  1. post
 *      1. post - Request Entity 사용시, Http Body -> object -> object mapper -> json -> rest template -> http body json으로 전송.
 *      2. postForLocation() : POST 요청 전송, 결과로 Header에 저장된 URI를 결과로 Return 받는다.
 *
 *  2. put
 *      1. put() 사용
 *
 *  3. exchange()
 *      1. HTTP 헤더를 만들 수 있고, 어떠한 HTTP 메서드도 사용 가능하다.
 *
 *  4. excute()
 *      1. Request/Response 콜백을 수정 할 수 있다.
 *      2. getForObject(), postForObject()는 execute()를 내부적으로 호출한다.
 *
 *  5. optionForAllow : 주어진 URL 주소에서 지원하는 HTTP 메서드를 조회한다.
 *
 *  TODO : generic Response 추가
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class RestTemplateService {

    private final RestTemplateConfig restTemplate1;
//    private final RestTemplate rest = new RestTemplate();

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

//        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<RestUserResponse> forEntityResult = restTemplate1.restTemplate().getForEntity(uri, RestUserResponse.class); // 결과.
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

//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate1.restTemplate().getForEntity(uri, String.class);

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

//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate1.restTemplate().getForEntity(uri, String.class);

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

//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate1.restTemplate().getForEntity(uri, String.class);

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

//        RestTemplate restTemplate = new RestTemplate();

        // 내부적으로, uri, dto(RequestBody로 인식) 필요함
        ResponseEntity<RestUserResponse> result = restTemplate1.restTemplate().postForEntity(uri, dto, RestUserResponse.class);

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

        // Header 의 사용의 또다른 예제
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Header Name", "Header Value");
//        HttpEntity request = new HttpEntity(headers); // Http Entity Request에 header를 넣어줌.
//        // -> 이어서 restTemplate.exchange()
//        restTemplate1.restTemplate().exchange(uri, HttpMethod.POST, request, String.class); // uri, Http Methods, Http Request Entity, Type을 지정해주면된다.


        // Header를 설정을 해줄때는 exchange를 사용. - Request Entity 에서 header,body,uri를 설정해 주었음.
        ResponseEntity<RestUserResponse> exchange = restTemplate1.restTemplate().exchange(requestEntity, RestUserResponse.class);

        log.info("return 값 상태코드 : {}", exchange.getStatusCode());
        log.info("return body : {}", exchange.getBody());

        return exchange;
    }


    // post - getForObject Response에 대한 Test
    public String getForObjectTest1() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/get1")
                .encode()
                .build()
                .toUri();

//        RestTemplate restTemplate = new RestTemplate();

        String forObject = restTemplate1.restTemplate().getForObject(uri, String.class);

        log.info("forObject Return : {}", forObject);

        return forObject;
    }

    // GetForEntity 의 Response값에 대한 Test 및 예제
    public ResponseEntity<String> get1Test() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/get1")
                .encode()
                .build()
                .toUri();

//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate1.restTemplate().getForEntity(uri, String.class);

        log.info("return Entity : {}", forEntity);
        log.info("return Header : {}", forEntity.getHeaders());
        log.info("return Body : {}", forEntity.getBody());
        log.info("return status code : {}", forEntity.getStatusCode());
        return forEntity;
    }//get1


    // Open API - 환율
    // USD 달러 기준 -> 각 통화 환율정보를 제공하는 API
    public double exchangeApi() {
        String forObject = restTemplate1.restTemplate().getForObject("https://open.er-api.com/v6/latest", String.class);

        Map<String, Map<String, Double>> map = restTemplate1.restTemplate().getForObject("https://open.er-api.com/v6/latest", Map.class);

        log.info("Dollar -> KRW = {}", map.get("rates").get("KRW"));

        double rrr = map.get("rates").get("KRW");

        return rrr;
    }// exchangeApi


    // Generic Type Return
    // RestTemplate의 응답 탕ㅂ을 지정할 때 사용.
    public ResGeneric<RestUserResponse> genericResponse() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user")
                .queryParam("id", "test1")
                .queryParam("name", "test11")
                .encode()
                .build()
                .toUri();

        // request DTO
        RestUserResponse dto = new RestUserResponse();
        dto.setId("dto1Id");
        dto.setName("dto1Name");

        // DTO 를 T Generic Type DTO에 set
        ResGeneric<RestUserResponse> reqDTO = new ResGeneric<>();
        reqDTO.setHeader(new ResGeneric.Header());
        reqDTO.setResBody(dto);

        // Request Entity
        RequestEntity<ResGeneric<RestUserResponse>> requestEntity = RequestEntity
                .post(uri)
                .header("server-header", "HeaderName")
                .contentType(MediaType.APPLICATION_JSON)
                .body(reqDTO);

        ResponseEntity<ResGeneric<RestUserResponse>> exchange = restTemplate1.restTemplate()
                .exchange(requestEntity, new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();

    }


}//class
