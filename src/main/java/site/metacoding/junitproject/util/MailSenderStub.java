package site.metacoding.junitproject.util;

public class MailSenderStub implements MailSender{
    @Override
    public boolean send() {
        return true;
    }
}
