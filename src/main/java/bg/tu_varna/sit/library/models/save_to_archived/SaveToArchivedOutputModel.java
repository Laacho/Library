package bg.tu_varna.sit.library.models.save_to_archived;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaveToArchivedOutputModel implements OperationOutput {
  private String message;
}
