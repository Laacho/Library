package bg.tu_varna.sit.library.models.check_if_codes_matches;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfCodesMatchesInputModel implements OperationInput {
    @NotBlank(message = "Verification code must not be blank")
    private String inputVerificationCode;

    @NotBlank(message = "New email must not be blank")
    @Email(message = "New email must be a valid email address")
    private String newEmail;
}
