package site.metacoding.junitproject.util;

import org.springframework.stereotype.Component;

@Component //IOC 컨테이너 등록
public class MailSenderAdapter implements MailSender{

    private Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean send() {
        return mail.sendMail();
//        return true;
    }
}
