package boot.bootprac.jwt_request.service;

import boot.bootprac.config.RestTemplateConfig;
import boot.bootprac.jwt_request.domain.JwtRequestDTO;
import boot.bootprac.jwt_request.domain.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/************
 * @info : Jwt 요청 서비스 - (API 서버에 요청 보내기)
 * @name : JwtRequestService
 * @date : 2023/03/18 3:51 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtRequestService {

    private final RestTemplateConfig restTemplate;

    // post
    public ResponseEntity<JwtResponseDTO> loginRequest(JwtRequestDTO dto) {
        // Uri
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/jwt/v1/request")
                .encode()
                .build()
                .toUri();

        // Request Entity
        RequestEntity<JwtRequestDTO> requestEntity = RequestEntity
                .post(uri)
                .header("auth-key", "testUser")
                .body(dto);

        // Rest Template
        ResponseEntity<JwtResponseDTO> exchange = restTemplate.restTemplate().exchange(requestEntity, JwtResponseDTO.class);
        log.info("JWT RESPONSE BODY: {}", exchange.getBody());
        log.info("JWT RESPONSE Status Code: {}", exchange.getStatusCode());

        return exchange;
    }// request v1

}
