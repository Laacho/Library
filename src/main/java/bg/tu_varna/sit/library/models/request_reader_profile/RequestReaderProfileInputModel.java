package bg.tu_varna.sit.library.models.request_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RequestReaderProfileInputModel implements OperationInput {
    private String username;
}
