package com.epam.summer.lab.utills;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private final String projectName;

    public SendMail(final String projectName) {
        this.projectName = projectName;
    }

    public void send(final String username, final String password, final String theme) {
        final String MESSAGE_NO_THEME = "The report is missed";
        final String MESSAGE_TITLE =  projectName + "  installed successfully";
        final String MESSAGE_SUCCESS = "Email sent successfully";

        Properties prop = getProperties();

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(username)
            );
            message.setSubject(theme != null? theme : MESSAGE_NO_THEME);
            message.setText(MESSAGE_TITLE);

            Transport.send(message);

            System.out.println(MESSAGE_SUCCESS);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }
}
