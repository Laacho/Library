package bg.tu_varna.sit.library.models.users_table_view;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UsersData {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String username;
    private Boolean verified;
    private LocalDate dateOfVerification;
    private String email;
    private Double rating;

}
