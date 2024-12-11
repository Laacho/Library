package bg.tu_varna.sit.library.models.save_to_discard;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaveToDiscardOutputModel implements OperationOutput {

    private String message;
}
