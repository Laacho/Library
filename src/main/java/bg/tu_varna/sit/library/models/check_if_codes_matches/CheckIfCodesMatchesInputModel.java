package bg.tu_varna.sit.library.models.check_if_codes_matches;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfCodesMatchesInputModel implements OperationInput {
    private String inputVerificationCode;
    private String newEmail;
}
