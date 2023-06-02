package ru.dvorobiev.getvkuserinfo.utils;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import ru.dvorobiev.getvkuserinfo.ErrorCode;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/** класс для вывода информации в Excel */
@Getter
public class ReportExcel {
    /**
     * объект рабочей книги
     */
    public HSSFWorkbook workbook;

    public HSSFSheet sheet;
    public List<UserInfo> userInfoList;
    /**
     * Сообщение об ошибке
     */
    private String errMessage;
    private String nameWorkBook;

    public ReportExcel(String nameWorkBook, String name_sheet, List<UserInfo> userInfoList) throws IOException {
        try {
            this.nameWorkBook=nameWorkBook;
            File file = new File(String.format("%s", this.nameWorkBook));
            POIFSFileSystem poifsFileSystem=new POIFSFileSystem(file);
            workbook = new HSSFWorkbook(poifsFileSystem);
        } catch (Exception e){
            workbook = new HSSFWorkbook();
        }
        finally {
            if (workbook.getSheet(name_sheet)!=null){
                sheet=workbook.getSheet(name_sheet);
            } else {
                sheet = workbook.createSheet(name_sheet);
            }
            this.userInfoList = userInfoList;
        }
    }

    /**
     * Метод формирует отчет в Excel
     *
     * @return : ErrorCode.XLS_OK, ErrorCode.XLS_ERR - в случае исключения, ErrorCode.XLS_EMPTY -
     * нет данных
     */
    public int createReport(String nameWorkBook) throws IOException {
        int rownum = 0;
        Cell cell;
        Row row;

        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        // Date
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        // miliseconds
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("First Name");
        cell.setCellStyle(style);
        // Id_Node
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Last Name");
        cell.setCellStyle(style);
        // Id_Obj
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("City");
        cell.setCellStyle(style);
        // Value
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Contacts");
        cell.setCellStyle(style);
        // Value
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("BDate");
        cell.setCellStyle(style);

        for (UserInfo userInfo : userInfoList) {
            rownum++;

            row = sheet.createRow(rownum);
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(userInfo.getUserId());
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(userInfo.getUserFirstName());
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(userInfo.getUserLastName());
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(userInfo.getUserCity());
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(userInfo.getUserContacts());
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(DateFormatted.getDateString(userInfo.getUserBDate()));
        }
        if (rownum > 0) {
            try {
                workbook.write(new FileOutputStream(nameWorkBook));
                workbook.close();
                return ErrorCode.XLS_OK;
            } catch (IOException e) {
                File file = new File(String.format(this.nameWorkBook));
                file.getParentFile().mkdir();

                FileOutputStream outFile = new FileOutputStream(file);
                workbook.write(outFile);

                errMessage = e.getMessage();
                return ErrorCode.XLS_ERR;
            }
        }
        errMessage = String.format("Count rows: %d. Report not create.", rownum);
        return ErrorCode.XLS_EMPTY;
    }

    /**
     * Задаем необходимый стиль для работы в документе
     *
     * @param workbook: путь и имя файла с выгрузкой данных
     * @return style: возвращаем объект стиля документа
     */
    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
