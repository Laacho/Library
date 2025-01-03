package bg.tu_varna.sit.library.models.check_if_codes_matches;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfCodesMatchesOutputModel implements OperationOutput {
    private String message;
}
