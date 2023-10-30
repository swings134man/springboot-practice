package boot.bootprac.etc_server.excel;

import boot.bootprac.common.config.Constants;
import boot.bootprac.common.jwt.User;
import boot.bootprac.common.jwt.backend.UserService;
import boot.bootprac.common.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
@RequestMapping("/excel/")
public class ExcelController {

    private final ExcelService service;
    private final UserService userService;

    @GetMapping("v1/member")
    public ResponseEntity<Object> excelDownMemberInfo(HttpServletResponse response, boolean downStatus) {

        Object o = service.excelDownMemberInfo(response, downStatus);
//        service.excelDownMemberInfo(response, downStatus)
        String name = UriUtils.encode("유저_정보_엑셀.xlsx", StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + name + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(service.excelDownMemberInfo(response, downStatus));
    }

    // Excel Down - DB retrieve -> Excel
    // 대용량 엑셀 다운로드
    @GetMapping(value = "v2/user", produces = "application/vnd.ms-excel")
    public void excelDownUser(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException {
        // Excel Info
        Map<String, Object> excelInfo = new HashMap<>();
        Map<String, Object> tempData = new HashMap<>();

        excelInfo.put(Constants.EX_FILE_NAME, "유저_정보_엑셀");
        excelInfo.put(Constants.EX_EXCEL_DATA, tempData);

        // Header List
        LinkedHashMap<String, String> headerList = new LinkedHashMap<>();
        headerList.put("userId", "유저ID");
        headerList.put("userName", "유저명");
        headerList.put("nickName", "닉네임");
        headerList.put("activated", "활성여부");
        headerList.put("password", "비밀번호");

        tempData.put(Constants.EX_HEADER_LIST, headerList);

        // Excel Down Logic - Util
        ExcelUtil.downloadExcelToSxssf(excelInfo, request, response, (sheet) -> {
            AtomicInteger rowIdx = new AtomicInteger(0); //

            // DB Select
            List<User> all = userService.findAll();
            for (User user: all) {
                // Data Row 입력
                Row row = sheet.createRow(rowIdx.incrementAndGet());
                int cellCnt = 0;
                row.createCell(cellCnt++).setCellValue(user.getUserId());
                row.createCell(cellCnt++).setCellValue(user.getUsername());
                row.createCell(cellCnt++).setCellValue(user.getNickname());
                row.createCell(cellCnt++).setCellValue(""); // ?
                row.createCell(cellCnt++).setCellValue(user.getPassword());
            }

        });

    }

}//class
