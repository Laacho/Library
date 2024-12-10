package bg.tu_varna.sit.library.utils.session;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
}
