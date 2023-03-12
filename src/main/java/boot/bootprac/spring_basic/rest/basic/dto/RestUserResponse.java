package boot.bootprac.spring_basic.rest.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : Rest Template Response DTO
 * @name : RestUserResponse
 * @date : 2023/03/12 7:35 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestUserResponse {

    private String id;
    private String name;
}
