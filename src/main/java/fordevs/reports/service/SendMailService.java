package fordevs.reports.service;

import fordevs.reports.model.EmailDetails;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
public class SendMailService implements Tasklet, EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

//    private static final Log log = LogFactory.getLog(SendMailService.class);
//    private SendMailService sendMailService;
//    private JavaMailSender mailSender;
//    private String senderAddress;
//    private String recipient;
//    private String attachmentFilePath;
//
//    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//    public void setSenderAddress(String senderAddress) {
//        this.senderAddress = senderAddress;
//    }
//
//    public void setRecipient(String recipient) {
//        this.recipient = recipient;
//    }
//
//    public void setAttachmentFilePath(String attachmentFilePath) {
//        this.attachmentFilePath = attachmentFilePath;
//    }
//
//    public void setSendMailService(SendMailService sendMailService) {
//        this.sendMailService = sendMailService;
//    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//        log.debug("execute(StepContribution contribution, ChunkContext chunkContext) begin");
//        sendMailService.setFields(mailSender, senderAddress, recipient, attachmentFilePath);
//        sendMailService.sendMail();
//        log.debug("execute(StepContribution contribution, ChunkContext chunkContext) end");
//        return RepeatStatus.FINISHED;
        System.out.println("Tasklet Send Mail");
        System.out.println(chunkContext.getStepContext().getJobExecutionContext());

        return RepeatStatus.FINISHED;
    }

    public String sendSimpleMail(EmailDetails details) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";

        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }


    // To send an email with attachment
    public String sendMailWithAttachment(EmailDetails details) {

        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";


        } catch (MessagingException e) {
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

}



