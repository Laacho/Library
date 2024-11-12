package bg.tu_varna.sit.library.models.register;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RegisterOutputModel implements OperationOutput {
    private String message;

}
