package fordevs.reports.config;

import fordevs.reports.model.InputFlatFile;
//import fordevs.reports.reader.InputFileReader;
import fordevs.reports.service.SendMailService;
import fordevs.reports.writer.Csv2Excel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class SampleJob {

    @Autowired JobBuilderFactory jobBuilderFactory;
    @Autowired StepBuilderFactory stepBuilderFactory;
    @Autowired Csv2Excel csv2Excel;
    @Autowired SendMailService sendMailService;


//    @Value("inputs/txtstudents.txt")
//    private Resource[] inputResources;

    private static final int CHUNK = 5;

    @Bean
    public Job chunkJob(){
        return jobBuilderFactory
                .get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .next(mailStep())
                .build();
    }
    @Bean
    public Step chunkStep(){
        return stepBuilderFactory.get("chunkStep")
                .<InputFlatFile, InputFlatFile>chunk(CHUNK)
                .reader(flatFileItemReader())
                //.processor(itemProcessor)
                .writer(csv2Excel)
                .build();
    }
    private Step mailStep() {
        return stepBuilderFactory.get("mailStep")
                .tasklet(sendMailService)
                //.listener(stepListener)
                .build();
    }

    @Bean
    public FlatFileItemReader<InputFlatFile> flatFileItemReader(){
        //Create reader instance
        FlatFileItemReader<InputFlatFile> itemReader =
                new FlatFileItemReader<>();

        //Set input file location
        itemReader.setResource(new FileSystemResource("inputs/txtstudents.txt"));

        //Set number of lines to skip. Use it if file has header rows.
        itemReader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        itemReader.setLineMapper(new DefaultLineMapper<>() {
            {
                //4 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer(",") {
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                    }
                });
                //Set values in InputFlatFile class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(InputFlatFile.class);
                    }
                });
            }
        });
        return itemReader;
    }

    // Multiple flat files reader
//    @Bean
//    public MultiResourceItemReader<InputFlatFile> multiResourceItemReader()
//    {
//        MultiResourceItemReader<InputFlatFile> resourceItemReader = new MultiResourceItemReader<>();
//        resourceItemReader.setResources(inputResources);
//        resourceItemReader.setDelegate(itemReader());
//        return resourceItemReader;
//    }

//    @Bean
//    public FileWriter fileWriter(){
//        return new FileWriter();
//    }
//
}
