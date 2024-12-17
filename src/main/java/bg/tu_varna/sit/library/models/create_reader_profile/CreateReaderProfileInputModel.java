package bg.tu_varna.sit.library.models.create_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CreateReaderProfileInputModel implements OperationInput {
    private Long id;
}
