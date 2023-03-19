package boot.bootprac.jwt_request.controller;


import boot.bootprac.jwt_request.domain.JwtRequestDTO;
import boot.bootprac.jwt_request.domain.JwtResponseDTO;
import boot.bootprac.jwt_request.service.JwtRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/************
 * @info : Jwt 요청 컨트롤러 - (API 서버에 요청 보내기)
 * @name : JwtRequestController
 * @date : 2023/03/18 4:07 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 해당 컨트롤러에 사용자의 ID,PW 를 요청받고 해당 내용으로 Jwt 요청
 * -> Sucecss : JWT 내용
 * -> Fail : Exception(?)
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/jwt/")
public class JwtRequestController {

    private final JwtRequestService service;


    /**
     * Jwt Authentication Server Request
     * @version : v1
     * @param dto
     * @return
     */
    @PostMapping("v1/request")
    public JwtResponseDTO loginRequest(@Valid @RequestBody JwtRequestDTO dto) {
        ResponseEntity<JwtResponseDTO> result = service.loginRequest(dto);

        if(result.getStatusCodeValue() == 200) {
            return result.getBody();
        }else {
            throw new IllegalStateException("API Server Error : (유효한 Request가 아님.)" + " msg: " + result.getBody().getMsg());
        }
    }// login request v1


    @GetMapping("/v1/token/get")
    public String getWithToken(@RequestHeader("Authorization") String header , @RequestParam String param) {
        ResponseEntity<String> result = service.getWithTokenS(header, param);
        return result.getBody();
    }


}//class
