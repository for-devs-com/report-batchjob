package fordevs.reports.writer;

import fordevs.reports.model.ExcelFile;
import fordevs.reports.model.InputFlatFile;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileWriter implements ItemWriter<InputFlatFile> {

    @Override
    public void write(List<? extends InputFlatFile> items) throws Exception, ParseException {
        System.out.println("Chunking...");
        for (InputFlatFile item : items) {
            System.out.println(item.toString());
        }
    }

}
