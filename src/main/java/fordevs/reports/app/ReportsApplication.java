package fordevs.reports.app;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"fordevs.reports.app",
		"fordevs.reports.config",
		"fordevs.reports.controller",
		"fordevs.reports.service",
		"fordevs.reports.processor",
		"fordevs.reports.writer",
		"fordevs.reports.reader"
})
@EnableBatchProcessing
public class ReportsApplication {

	@Autowired
	Job job;

	@Autowired
	JobLauncher jobLauncher;

	public static void main(String[] args) {
		SpringApplication.run(ReportsApplication.class, args);
	}

	public void perform() throws Exception {
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);
	}

}



