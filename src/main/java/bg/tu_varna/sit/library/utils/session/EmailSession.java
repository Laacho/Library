package bg.tu_varna.sit.library.utils.session;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import lombok.*;


@Singleton
public class EmailSession {
    public static String fromEmail;
    public static String toEmail;
    private EmailSession(){}
}
