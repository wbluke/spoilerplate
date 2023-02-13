package spoilerplate.testing.spring.client.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MailSendClient {

//    private final JavaMailSender mailSender;

    public boolean send(String fromEmail, String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);

        try {
//            mailSender.send(message);
            return true;
        } catch (Exception e) {
            log.error("메일 전송 실패 : {}", e.getMessage());
            return false;
        }
    }

}
