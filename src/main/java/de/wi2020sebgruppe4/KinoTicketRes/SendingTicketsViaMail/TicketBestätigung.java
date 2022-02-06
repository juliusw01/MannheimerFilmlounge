package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

public class TicketBestätigung {

    public static Message prepareMessage(Session session, String myAccount, String empfaenger, String titel, String vorname){
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(empfaenger));
            message.setSubject("Deine Buchung war erfolgreich!");

            //message.setText("Das ist eine Test Mail. Hat es geklappt?");


            // Multipart-Message ("Wrapper") erstellen
            Multipart multipart = new MimeMultipart();
            // Body-Part setzen:
            BodyPart messageBodyPart = new MimeBodyPart();
            // Textteil des Body-Parts
            messageBodyPart.setText("Hallo " + vorname + ", \n \nvielen Dank für Deine Buchung! \n \n" +
                    "Hiermit bestätigen wir deine Buchung zu dem Film " + titel + ". \n\n" +
                    //"Sie haben die Vorstellung am " + date + " um " + time + "Uhr gebucht.\n \n" +
                    "Einen angenehmen Aufenthalt und viel Spaß wünscht Dir " +
                    "die Mannheimer Filmlounge");
            // Body-Part dem Multipart-Wrapper hinzufügen
            multipart.addBodyPart(messageBodyPart);
            // Message fertigstellen, indem sie mit dem Multipart-Content ausgestattet wird
            //message.setContent(JavaMailHtml.getJavaMailHtml(), "text/html");
            message.setContent(multipart);

            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
