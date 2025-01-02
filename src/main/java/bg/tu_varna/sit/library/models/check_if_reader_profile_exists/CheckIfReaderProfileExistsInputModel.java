package bg.tu_varna.sit.library.models.check_if_reader_profile_exists;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CheckIfReaderProfileExistsInputModel implements OperationInput {
    private String username;
}
