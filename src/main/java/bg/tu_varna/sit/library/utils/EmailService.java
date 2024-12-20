package bg.tu_varna.sit.library.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EmailService {
    private static final String SENDER_EMAIL = System.getProperty("EMAIL");
    private static final String APP_PASSWORD = System.getProperty("APP_PASSWORD");
    private static final int LENGTH_VERIFICATION_CODE = 6;

    private static final Mailer MAILER = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, SENDER_EMAIL, APP_PASSWORD)
            .withTransportStrategy(org.simplejavamail.api.mailer.config.TransportStrategy.SMTP_TLS)
            .buildMailer();
   public static final  String headerWithVerificationCode = "<html>" +
            "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background: linear-gradient(to bottom, #87CEEB, #FFFFFF); color: #333;'>" +
            "   <div style='max-width: 600px; margin: 20px auto; background: #FFFFFF; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;'>" +
            "       <header style='background: #4682B4; color: white; text-align: center; padding: 20px;'>" +
            "           <h1 style='margin: 0; font-size: 24px;'>Your Verification Code</h1>" +
            "       </header>" +
            "       <div style='padding: 20px; text-align: center;'>" +
            "           <p style='font-size: 16px; color: #555;'>Hello,</p>" +
            "           <p style='font-size: 16px; color: #555;'>Thank you for using our service! Below is your verification code:</p>" +
            "           <div style='margin: 20px 0; padding: 10px; background: #87CEEB; color: white; font-size: 24px; font-weight: bold; border-radius: 5px;'>" +
            "              {{verificationCode}}" +
            "           </div>" +
            "           <p style='font-size: 14px; color: #555;'>Please use this code to complete your verification. If you didn’t request this code, please ignore this email.</p>" +
            "       </div>" +
            "       <footer style='background: #F0F8FF; color: #888; text-align: center; font-size: 12px; padding: 10px;'>" +
            "           <p style='margin: 0;'>This is an automated message. Please do not reply.</p>" +
            "           <p style='margin: 0;'>© 2024 Library</p>" +
            "       </footer>" +
            "   </div>" +
            "</body>" +
            "</html>";

   public static final String headerWithMessage= """
           <html>
           <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background: linear-gradient(to bottom, #87CEEB, #FFFFFF); color: #333;">
               <div style="max-width: 600px; margin: 20px auto; background: #FFFFFF; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;">
                   <header style="background: #4682B4; color: white; text-align: center; padding: 20px;">
                       <h1 style="margin: 0; font-size: 24px;">{{messageTitle}}</h1>
                   </header>
                   <div style="padding: 20px; text-align: center;">
                       <p style="font-size: 16px; color: #555;"> This message was sent by the admin.</p>
                       <div style="margin: 20px 0; padding: 15px; background: #87CEEB; color: white; font-size: 16px; border-radius: 5px; font-weight: bold;">
                          {{messageContent}}
                       </div>
                       <p style="font-size: 14px; color: #888;">If you have any questions, please contact support.</p>
                   </div>
                   <footer style="background: #F0F8FF; color: #888; text-align: center; font-size: 12px; padding: 10px;">
                       <p style="margin: 0;">This is an automated message. Please do not reply.</p>
                       <p style="margin: 0;">© 2024 Library</p>
                   </footer>
               </div>
           </body>
           </html>
           """;

    public static void sendMail(String recipientEmail,String verificationCode) {
        String htmlContent = buildHTMLContent(verificationCode);
        Email email = buildEmail(recipientEmail, htmlContent);
        MAILER.sendMail(email);
    }
    public static void sendContactMail(String recipientEmail,String title,String body) {
        String htmlContent = buildHTMLContentWithMessage(title, body);
        Email email=buildEmailWithMessage(recipientEmail,htmlContent);
        MAILER.sendMail(email);
    }

    private static Email buildEmailWithMessage(String recipientEmail, String htmlContent) {
        return EmailBuilder.startingBlank()
                .from("Library",SENDER_EMAIL)
                .to(recipientEmail)
                .withSubject("Contact Email")
                .withHTMLText(htmlContent)
                .buildEmail();
    }

    private static String buildHTMLContentWithMessage(String title, String body) {
       return headerWithMessage
               .replace("{{messageTitle}}", title)
               .replace("{{messageContent}}", body);
    }


    private static Email buildEmail(String recipientEmail, String htmlContent) {
        return EmailBuilder.startingBlank()
                .from("Library", SENDER_EMAIL)
                .to(recipientEmail)
                .withSubject("Your Verification Code")
                .withHTMLText(htmlContent)
                .buildEmail();
       }

    private static @NotNull String buildHTMLContent(String verificationCode) {
       return headerWithVerificationCode.replace("{{verificationCode}}", verificationCode);
    }

    public static String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(LENGTH_VERIFICATION_CODE, characters);
    }
}
