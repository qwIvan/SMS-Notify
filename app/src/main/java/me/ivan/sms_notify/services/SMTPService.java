package me.ivan.sms_notify.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by ivan on 16-4-25.
 */
public class SMTPService {

    private boolean tls;
    private String host;
    private int port;
    private String from;
    private String password;
    private String to;

    public SMTPService(String from, boolean tls, String host,
                       int port, String password, String to) {
        this.from = from;
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.password = password;
        this.to = to;
    }

//    public SMTPService(Context context) {
//        tls = sharedPreferences.getBoolean("tls",false);
//        host = sharedPreferences.getString("host","smtp.qq.com");
//        port = sharedPreferences.getInt("port",587);
//        from = sharedPreferences.getString("from", "");
//        password = sharedPreferences.getString("password", "");
//        String to = sharedPreferences.getString("to","");
//    }

    public void send(String title,String content)
            throws MessagingException {


        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", String.valueOf(tls));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(title);
        message.setText(content);

        Transport.send(message);
    }
}
