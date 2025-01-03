package bg.tu_varna.sit.library.models.confirm_registration;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class ConfirmRegistrationOutputModel implements OperationOutput {
}
