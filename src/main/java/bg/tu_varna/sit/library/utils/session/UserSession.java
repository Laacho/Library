package bg.tu_varna.sit.library.utils.session;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Singleton
public class UserSession {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String username;
    private String password;
    private Boolean verified;
    private String verificationCode;
    private LocalDate dateOfVerification;
    private String email;
    private Boolean admin;
    private Double rating;
    private UserSession() {}
}
