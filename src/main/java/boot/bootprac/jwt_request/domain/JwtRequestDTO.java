package boot.bootprac.jwt_request.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/************
 * @info : Jwt API 테스트를 위한 Request DTO
 * @name : JwtRequestDTO
 * @date : 2023/03/18 4:05 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : @NotEmpty : not null, 공백 X
 ************/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDTO {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPw;

    // User Info
    private String userName;
    private String userPhone;
    private String userEmail;


}
