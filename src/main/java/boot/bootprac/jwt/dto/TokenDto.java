package boot.bootprac.jwt.dto;

import lombok.*;

/************
 * @info : ToKen 정보 Response DTO
 * @name : TokenDTO
 * @date : 2022/12/15 2:02 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}
