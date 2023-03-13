package boot.bootprac.spring_basic.rest.kakao.service;

import boot.bootprac.trace.TraceStatus;
import boot.bootprac.trace.trace_final.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/************
 * @info : Kakao Open API - Translate by Rest template
 * @name : TransKakaoService
 * @date : 2023/03/13 3:39 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class TransKakaoService {

    private final LogTrace trace;

    private String url = "https://dapi.kakao.com/v2/translation/translate";
    private String key = "kakao API key";

    // 번역

    /**
     * 카카오 번역 api를 통해 번역함
     * @param targetLang : en(영어), 번역 결과 언어
     * @param targetText : 번역할 텍스트
     */
    public String translate(String targetLang, String targetText) {
        String result = ""; // return value

        TraceStatus status = null;
        status = trace.begin("TransKakaoService.translate()");

        UriComponents build = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("src_lang", "kr")
                .queryParam("target_lang", targetLang)
                .queryParam("query", targetText)
                .encode()
                .build();

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + key);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange
                = restTemplate.exchange(build.toUri(), HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // json parsing - json 형태의 결과를 파싱함.
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(exchange.getBody());
            JSONArray translateText = (JSONArray) jsonObject.get("translated_text");

            List<String> str = (List<String>) translateText.get(0);
            result = str.stream().collect(Collectors.joining(" "));

            trace.end(status);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }//translate

}
