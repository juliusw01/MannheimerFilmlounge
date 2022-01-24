package de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail;

public class JavaMailHtml {

    private static String JAVA_MAIL_HTML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Buchungsbestätigung</title>\n" +
            "    <h1>Ihre Buchung war erfolgreich! </h1>\n" +
            "\n" +
            "    <p>Lieber Kunde,</p>\n" +
            "    <p>Ihre Buchung ist bei uns eingegangen. Wir wünschen Ihnen viel Spaß bei Ihrem Film und freuen uns auf Ihren Besuch!</p>\n" +
            "    <p>Mit freundlichen Grüßen</p>\n" +
            "    <p>Gruppe 4</p>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static String getJavaMailHtml() {
        return JAVA_MAIL_HTML;
    }
}
