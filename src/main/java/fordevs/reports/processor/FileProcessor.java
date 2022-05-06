package fordevs.reports.processor;

//import fordevs.reports.model.ExcelFile;
import fordevs.reports.model.InputFlatFile;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor  implements ItemProcessor<InputFlatFile, InputFlatFile> {


    @Override
    public InputFlatFile process(InputFlatFile inputFlatFile) throws Exception {
        InputFlatFile inputProcessed = new InputFlatFile();

        System.out.println("Inside FileProcessor");
        return inputProcessed;
    }
}

