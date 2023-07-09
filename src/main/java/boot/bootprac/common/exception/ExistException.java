package boot.bootprac.common.exception;

/************
 * @info : Custom Exception
 * @name : ExistException
 * @date : 2023/07/09 2:36 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 중복 데이터 save 불가시, DB 조회 후 존재시 발생하는 Exception Class
 ************/
public class ExistException extends RuntimeException{

    private String message;
    private String targetData;

    public ExistException(String message, String targetData) {
        super("이미 존재하는 데이터 입니다.");
        this.message = message;
        this.targetData = targetData;
    }
}
