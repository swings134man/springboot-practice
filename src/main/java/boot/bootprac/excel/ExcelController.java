package boot.bootprac.excel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/************
 * @info : Excel 다운로드를 위한 컨트롤러.
 * @name : ExcelController
 * @date : 2022/11/26 5:26 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/excel/v1/")
public class ExcelController {

    private final ExcelService service;

    @GetMapping("member")
    public ResponseEntity<Object> excelDownMemberInfo(HttpServletResponse response, boolean downStatus) {

        Object o = service.excelDownMemberInfo(response, downStatus);
//        service.excelDownMemberInfo(response, downStatus)
        String name = UriUtils.encode("유저_정보_엑셀.xlsx", StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + name + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(service.excelDownMemberInfo(response, downStatus));
    }

}//class
