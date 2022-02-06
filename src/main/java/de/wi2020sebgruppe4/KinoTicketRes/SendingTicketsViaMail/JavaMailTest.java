package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class JavaMailTest {

    public static void main(String[] args) {
        try {
            JavaMail.sendTicketConformationMail("jwa_home@hotmail.de", "Terminator", "Julius");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
