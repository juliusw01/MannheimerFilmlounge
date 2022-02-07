package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class JavaMail {

    private Properties setProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",  "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    private String myAccount = "noreply.kinores@gmail.com";
    private String myPassword = "buwni4-nixvoc-xydjUt";

    public void sendTicketConformationMail(String empfaenger, String movieTitel, String vorname) {
        try {
        	
        	empfaenger = empfaenger.replace(" ", "");

            Properties properties = setProperties();

            empfaenger.trim();

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccount, myPassword);
                }
            });

            // Message-Objekt erzeugen und senden!

            Message message = prepareMessage(session, myAccount, empfaenger, movieTitel, vorname);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} // E-Mail senden!
    }

    public Message prepareMessage(Session session, String myAccount, String empfaenger, String titel, String vorname){
        try {

            empfaenger.trim();

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
            messageBodyPart.setText("");
            // Body-Part dem Multipart-Wrapper hinzufügen
            multipart.addBodyPart(messageBodyPart);
            // Message fertigstellen, indem sie mit dem Multipart-Content ausgestattet wird
            //message.setContent(JavaMailHtml.getJavaMailHtml(), "text/html");
            message.setContent("Hallo " + vorname + ", \n \nvielen Dank für Deine Buchung! \n \n" +
                    "Hiermit bestätigen wir deine Buchung zu dem Film " + titel + ". \n\n" +
                    //"Sie haben die Vorstellung am " + date + " um " + time + "Uhr gebucht.\n \n" +
                    "Einen angenehmen Aufenthalt und viel Spaß wünscht Dir " +
                    "die Mannheimer Film Lounge", "text/html");

            return message;
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }
    }

    /*
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

     */
}
