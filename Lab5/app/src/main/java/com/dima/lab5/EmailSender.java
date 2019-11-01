package com.dima.lab5;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class EmailSender
{
    private static final String SMTP_SERVER    = "smtp.gmail.com";
    private static final String SMTP_PORT      = "587";
    private static final String SMTP_AUTH_USER = "ceoofchat@gmail.com";
    private static final String SMTP_AUTH_PWD  = "z2k4ccm4os2";
    private static final String EMAIL_FROM     = "ceoofchat@gmail.com";

    private Message message = null;

    void sendMessage (final String text)
    {
        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            message.setContent(mmp);
            Transport.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }


    EmailSender(final String emailTo, final String thema)
    {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        try {
            Authenticator auth = new EmailAuthenticator(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true);

            InternetAddress emailFromAddress = new InternetAddress(EMAIL_FROM);
            InternetAddress emailToAddress = new InternetAddress(emailTo);
            message = new MimeMessage(session);
            message.setFrom(emailFromAddress);
            message.setRecipient(Message.RecipientType.TO, emailToAddress);
            message.setSubject(thema);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}