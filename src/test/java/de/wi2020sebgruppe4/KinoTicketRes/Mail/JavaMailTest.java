package de.wi2020sebgruppe4.KinoTicketRes.Mail;

import de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail.JavaMail;
import de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail.TicketBestätigung;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")

public class JavaMailTest {

    @BeforeAll
    public void beforeAll(){

    }

    @DisplayName("sendTicketConfirmationMalTest")
    @Test
    public void sendTicketConfirmationMalTest(){
        JavaMail.sendTicketConformationMail("noreply.kinores@gmail.com", "Shrek", "Julius");

    }

    @DisplayName("Ungültige Mailadresse")
    @Test
    public void snedMailWithWrongMailAddress(){
        try {
            JavaMail.sendTicketConformationMail("ungültige mail", "Shrek", "Julius");
        }catch (Exception e){}

    }

    @Test
    public void kunstruktorTest(){
        JavaMail testMail = new JavaMail();
    }

    @Test
    public void testMailException(){
        try {
            JavaMail.sendTicketConformationMail(null, "Shrek", "Julius");
        }catch (Exception e){
        }
    }

    @Test
    public void ticketbestätigungKonstruktorTest(){
        TicketBestätigung ticketBestätigung = new TicketBestätigung();
    }

    @Test
    public void ticketBestätigungExceptionTest(){
        try{
            TicketBestätigung.prepareMessage(null, null, null, null, null);
        }catch (Exception e){

        }
    }



}
