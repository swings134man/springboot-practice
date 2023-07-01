package boot.bootprac.etc_server.excel;

import boot.bootprac.cms.board.BoardService;
import boot.bootprac.cms.member.domain.Member;
import boot.bootprac.cms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/************
 * @info : 엑셀 파일 다운로드를 위한 서비스.
 * @name : ExcelService
 * @date : 2022/11/26 5:26 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExcelService {

    private final BoardService boardService;
    private final MemberService memberService;


    public Object excelDownMemberInfo(HttpServletResponse response,boolean downStatus) {
        // Board ALl info List
        List<Member> resultList = memberService.findMember();// 회원 전체.
//        List<BoardDTO> resultList = boardService.findAll();

        if(resultList.isEmpty()) {
            throw new IllegalArgumentException("해당 정보가 존재하지 않습니다.");
        }

        // Excel down check
        if(downStatus) {
            dataToExcel(response, resultList);
            return null; // Excel 변환후 result는 response내부에 존재하기 때문에 아무것도 넘기지 않음.
        }

        return resultList;

    }// method

    /**
     * @info    : data를 엑셀로 변환
     * @name    : dataToExcel
     * @date    : 2022/11/26 5:39 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    private void dataToExcel(HttpServletResponse response, List<Member> list) {

        try{
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("유저 정보 리스트");

            // 숫자 포맷은 아래의 numberCellType을 적용 함. --> 금액관련 세팅.
//            CellStyle numberCellStyle = workbook.createCellStyle();
//            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            // File명
            final String fileName = "유저_정보_리스트";

            // 헤더(엑셀 Head Row)
//            String[] header = {"게시판아이디","제목","작성자", "작성일","수정일","내용"};
            String[] header = {"아이디", "이름"};
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            // Data (Excel Body)
            for (int i = 1; i < list.size(); i++) {
                row = sheet.createRow(i);

                Member member = list.get(i);

                Cell cell = null;
                // 유저 아이디
                cell = row.createCell(0);
                cell.setCellValue(member.getId());
                // 유저 이름
                cell = row.createCell(1);
                cell.setCellValue(member.getName());

                // 작성자
//                cell = row.createCell(2);
//                cell.setCellValue(boardList.getWriter());
//                // 작성일
//                cell = row.createCell(3);
//                cell.setCellValue(boardList.getInsertDate());
//                // 수정일
//                cell = row.createCell(4);
//                cell.setCellValue(boardList.getModifiedDate());
//                // 내용
//                cell = row.createCell(5);
//                cell.setCellValue(boardList.getContent());
            }// for


            // Response setting
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
            // 파일명은 URLEncoder로 감싸줄것.

            workbook.write(response.getOutputStream()); // response 의 파일에 Excel 파일 전송.
            workbook.close();
        }catch (IOException e) {
            e.printStackTrace();
        }//try catch
    }// excel

}//class
