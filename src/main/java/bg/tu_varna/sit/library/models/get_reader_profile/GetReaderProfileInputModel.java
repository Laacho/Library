package bg.tu_varna.sit.library.models.get_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetReaderProfileInputModel implements OperationInput {
    private String username;
}
