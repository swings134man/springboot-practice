package boot.bootprac.trace.trace_final;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/************
 * @info : FileLogTrace를 bean으로 사용하기위한 config 클래스
 * @name : LogTraceConfig
 * @date : 2023/03/07 2:00 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Configuration
public class LogTraceConfig {

    // 구체적으로 명시된 타입이 아닌, -> 인터페이스 타입으로 set
    @Bean
    public LogTrace logTrace() {
        return new FiledLogTrace();
    }
}
