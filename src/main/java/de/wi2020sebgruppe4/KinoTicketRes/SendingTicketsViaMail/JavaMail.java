package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

import javax.mail.*;
import java.util.Date;
import java.util.Properties;

public class JavaMail {

    private static Properties setProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",  "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    private static String myAccount = "noreply.kinores@gmail.com";
    private static String myPassword = "buwni4-nixvoc-xydjUt";

    public static void sendTicketConformationMail(String empfaenger, String movieTitel, Date date) throws Exception{

        Properties properties = setProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, myPassword);
            }
        });

        // Message-Objekt erzeugen und senden!

        Message message = TicketBestätigung.prepareMessage(session, myAccount, empfaenger, movieTitel, date);
        Transport.send(message); // E-Mail senden!
    }

    public static void sendMailAdressConformationLink(String empfaenger, String conformationLink) throws MessagingException {
        Properties properties = setProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, myPassword);
            }
        });

        // Message-Objekt erzeugen und senden!

        Message message = MailBestätigen.prepareMessag(session, myAccount, empfaenger);
        Transport.send(message); // E-Mail senden!
    }
}
