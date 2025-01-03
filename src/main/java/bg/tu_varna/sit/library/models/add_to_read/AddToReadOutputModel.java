package bg.tu_varna.sit.library.models.add_to_read;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddToReadOutputModel implements OperationOutput {
    private String message;
}
