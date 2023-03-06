package boot.bootprac.logtracer.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/************
 * @info : Log 추적기를 위한 Repository class
 * @name : OrderRepositoryVO
 * @date : 2023/03/06 5:19 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {
        // save Logic
        if(itemId.equals("ex")) { // itemId=ex인 경우 예외 발생
            throw new IllegalStateException("에외 발생.");
        }
        sleep(1000);
    }//save

    /**
     * Thread 사용, millis 초간 일시정지.
     * @param millis
     */
    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }// sleep

}//class
