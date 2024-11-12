package bg.tu_varna.sit.library.models.register;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RegisterInputModel implements OperationInput {

    @Size(min = 0, max = 100)
    @NotNull
    private String firstName;
    @Size(min = 0, max = 100)
    @NotNull
    private String lastName;
    @Past
    private LocalDate birthdate;
    @Size(min = 0, max = 100)
    @NotNull
    private String username;
    @Size(min = 0, max = 100)
    @NotNull
    private String password;
    @Email
    @NotNull
    private String email;
}
