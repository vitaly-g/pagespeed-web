package ru.itex.ssl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by PC-020 on 24.01.2017.
 */
public class Sender {
    private String username;
    private String password;
    private Properties props;

    public Sender(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    public void send(String toEmail, String subject, String text, String url, String deviceType){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("На почту технической поддержки отправлено оповещение о критическом состоянии " +
                    (deviceType.equals("desktop") ? "десктопной" : "мобильной") + " версии сайта " + url + "!");
        } catch (MessagingException e) {
            System.out.println("Оповещение о критическом состоянии не отправлено!");
            throw new RuntimeException(e);
        }
    }
}
