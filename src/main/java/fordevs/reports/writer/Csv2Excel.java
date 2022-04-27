package fordevs.reports.writer;

import fordevs.reports.model.InputFlatFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class Csv2Excel implements ItemStreamWriter<InputFlatFile> {
    private int currentRow;
    HSSFWorkbook wb;


    public void open(ExecutionContext executionContext) throws ItemStreamException {
        wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("test sheet");  // new sheet
        currentRow = 0;
        createHeaderRow(sheet);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    private void createHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(currentRow);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        sheet.setColumnWidth(0, colWidth(6.0)); // Custom column width

        cell = row.createCell(1);
        cell.setCellValue("FIRST NAME");
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("LAST NAME");
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("EMAIL");
        sheet.autoSizeColumn(3);
        currentRow++;

    }

    public int colWidth(double width){
        return (int) Math.round(width * 256 + 200);
    }

    public void write(List<? extends InputFlatFile> items) throws Exception {
        Sheet sheet = wb.getSheetAt(0);

        for (InputFlatFile item : items) {
            Row row = sheet.createRow(currentRow);


            row.createCell(0).setCellValue(item.getId());
            //sheet.autoSizeColumn(0);
            row.createCell(1).setCellValue(item.getFirstName());
            sheet.autoSizeColumn(1);
            row.createCell(2).setCellValue(item.getLastName());
            sheet.autoSizeColumn(2);
            row.createCell(3).setCellValue(item.getEmail());
            sheet.autoSizeColumn(3);
            currentRow++;

        }


    }

    @Override
    public void close() throws ItemStreamException {
        if (wb == null) {
            return;
        }
        //Generating output file
        try (OutputStream fileOut = new FileOutputStream("src/main/resources/reports/workbook.xls")) {
            wb.write(fileOut);
        } catch (IOException ex) {
            throw new ItemStreamException("Error writing to output file", ex);
        }
        currentRow = 0;
    }
}
