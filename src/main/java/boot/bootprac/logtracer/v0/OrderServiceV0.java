package boot.bootprac.logtracer.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/************
 * @info : Log 추적기를 위한 Service class
 * @name : OrderService
 * @date : 2023/03/06 5:18 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

    private final OrderRepositoryV0 repositoryVO;


    public void orderItem(String itemId) { // to save
        repositoryVO.save(itemId);
    }

}
