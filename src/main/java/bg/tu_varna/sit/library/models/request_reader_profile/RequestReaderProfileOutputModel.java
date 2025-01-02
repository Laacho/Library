package bg.tu_varna.sit.library.models.request_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RequestReaderProfileOutputModel implements OperationOutput {
    private String message;
}
