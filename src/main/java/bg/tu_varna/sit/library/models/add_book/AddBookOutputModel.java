package bg.tu_varna.sit.library.models.add_book;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString

public class AddBookOutputModel implements OperationOutput {
    private String message;
}
