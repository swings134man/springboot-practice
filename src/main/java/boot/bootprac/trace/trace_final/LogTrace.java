package boot.bootprac.trace.trace_final;

import boot.bootprac.trace.TraceStatus;

/************
 * @info : Log 추적기 interface
 * @name : LogTrace
 * @date : 2023/03/07 1:34 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
public interface LogTrace {

    TraceStatus begin(String message); // Return: TraceStatus -> By FiledLogTrace.class
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
