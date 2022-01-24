package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

import javax.mail.*;
import javax.mail.internet.*;

public class MailBestätigen {

    public static Message prepareMessag(Session session, String myAccount, String empfänger){
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(empfänger));
            message.setSubject("Bitte bestätigen Sie Ihre E-Mail Adresse");

            Multipart multipart = new MimeMultipart();

            BodyPart bodyPart = new MimeBodyPart();

            bodyPart.setText("Lieber Kunde,\n\n" +
                    "bitte bestätigen Sie Ihre E-Mail Adresse. Klicken Sie dafür auf den untenstehenden Link.\n\n" +
                    "Freundliche Grüße\n" +
                    "Ihr Kino");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
