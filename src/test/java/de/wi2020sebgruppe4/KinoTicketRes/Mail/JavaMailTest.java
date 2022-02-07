package de.wi2020sebgruppe4.KinoTicketRes.Mail;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail.JavaMail;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class JavaMailTest {
	
	@Autowired
	JavaMail mail;

    @DisplayName("Prepare Email")
	@Test
	void testPrepareMessage() {
		
    	Properties properties = new Properties();
        properties.put("mail.smtp.auth",  "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreply.kinores@gmail.com", "buwni4-nixvoc-xydjUt");
            }
        });
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
            	mail.prepareMessage(session, "noreply.kinores@gmail.com", "jwa_home@hotmail.de", "Terminator", "Julius");          
            }
        });
    	
	}

    @DisplayName("Prepare Email Exception")
	@Test
	void testPrepareMessageException() {
		
    	Properties properties = new Properties();
        properties.put("mail.smtp.auth",  "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreply.kinores@gmail.com", "buwni4-nixvoc-xydjUt");
            }
        });
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
            	mail.prepareMessage(session, null, null, null, null);          
            }
        });
    	
	}
	
	@Test
	void testSendMessage() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
            	mail.sendTicketConformationMail("jwa_home@hotmail.de", "Terminator", "Julius");
            }
        });
		
	}
	
	@Test
	void testSendMessageException() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
            	mail.sendTicketConformationMail(null, null, null);
            }
        });
		
	}

//    @DisplayName("sendTicketConfirmationMalTest")
//    @Test
//    public void sendTicketConfirmationMalTest(){
//        JavaMail.sendTicketConformationMail("noreply.kinores@gmail.com", "Shrek", "Julius");
//
//    }
//
//    @DisplayName("Ungültige Mailadresse")
//    @Test
//    public void snedMailWithWrongMailAddress(){
//        try {
//            JavaMail.sendTicketConformationMail("ungültige mail", "Shrek", "Julius");
//        }catch (Exception e){}
//
//    }
//
//    @Test
//    public void kunstruktorTest(){
//        JavaMail testMail = new JavaMail();
//    }
//
//    @Test
//    public void testMailException(){
//        try {
//            JavaMail.sendTicketConformationMail(null, "Shrek", "Julius");
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void ticketbestätigungKonstruktorTest(){
//        TicketBestätigung ticketBestätigung = new TicketBestätigung();
//    }
//
//    @Test
//    public void ticketBestätigungExceptionTest(){
//        try{
//            TicketBestätigung.prepareMessage(null, null, null, null, null);
//        }catch (Exception e){
//
//        }
//    }

}
