package boot.bootprac.aop.first;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : AOP Controller Test를 위한 VO Class
 * @name : UserVO
 * @date : 2023/03/16 5:45 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private String id;
    private String name;
    private String phone;

}
