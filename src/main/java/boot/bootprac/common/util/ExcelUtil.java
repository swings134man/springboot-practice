package boot.bootprac.common.util;

import boot.bootprac.common.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    /**
     * 대용량 데이터 다운로드용 스트림 API - xlsx
     * @param model
     * @param request
     * @param response
     * @param addRow
     */
    public static void downloadExcelToSxssf(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, AddRowToSXSSF addRow) {
        try {
            // 입력받은 정보 가져오기
            Map<String, Object> excelData = (Map<String, Object>) model.get(Constants.EX_EXCEL_DATA);

            // 다운로드시 사용할 파일명 가져오기
            String rawFileName = (String) model.get(Constants.EX_FILE_NAME);
            String fileName = "";
            if (!StringUtils.isEmpty(rawFileName)) {
                fileName = createFileName(rawFileName) + "x";
                setFileNameToResponse(request, response, fileName);
            } else {
                rawFileName = Constants.EX_EXCEL_DATA;
            }

            // 워크북 생성

            SXSSFWorkbook workbook = new SXSSFWorkbook();
            // 워크북에 새로운 시트를 생성
            SXSSFSheet sheet = workbook.createSheet(rawFileName);

            // 헤더 배경색 정보 가져오기
            IndexedColors headerBgColor = IndexedColors.YELLOW;
            if (excelData.containsKey(Constants.EX_HEADER_BG_COLOR)) {
                headerBgColor = (IndexedColors) excelData.get(Constants.EX_HEADER_BG_COLOR);
            }

            // 헤더 스타일 입력
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 단색 배경
            headerStyle.setFillForegroundColor(headerBgColor.getIndex());

            // 헤더 정보 가져오기
            LinkedHashMap<String, String> headerList = (LinkedHashMap<String, String>) excelData.get(Constants.EX_HEADER_LIST);

            // 헤더 입력
            Row headerRow = sheet.createRow(0);
            Iterator<String> keys = headerList.keySet().iterator();
            int headerCnt = 0;
            while (keys.hasNext()) {
                String headerKey = keys.next();
                Cell headerCell = headerRow.createCell(headerCnt);
                headerCell.setCellValue(headerList.get(headerKey));
                headerCell.setCellStyle(headerStyle);
                headerCnt++;
            }

            // 데이터 목록 가져오기
            addRow.row(sheet);

            response.setContentType(Constants.EX_CONTENT_TYPE);

            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);

            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static String createFileName(String fname) throws UnsupportedEncodingException {
        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String rs = new StringBuilder(fname)
                .append("_")
                .append(fileFormat.format(new Date()))
                .append(".xls")
                .toString();
        return URLEncoder.encode(rs, "UTF-8");
    }

    private static void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("MSIE 5.5") >= 0) {
            response.setContentType("doesn/matter");
            response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }
        response.setHeader("Content-Transfer-Encoding", "binary");

    }

    @FunctionalInterface
    public interface AddRowToSXSSF {
        void row(SXSSFSheet sheet);
    }

}
