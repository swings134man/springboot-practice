package boot.bootprac.spring_basic.rest.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : Generic Response Type DTO
 * @name : ResGeneric
 * @date : 2023/03/15 2:06 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResGeneric<T> {

    private Header header;

    private T resBody;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header{
        private String resCode;
    }
}
