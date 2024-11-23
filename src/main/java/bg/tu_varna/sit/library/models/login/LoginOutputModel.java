package bg.tu_varna.sit.library.models.login;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import bg.tu_varna.sit.library.utils.session.UserSession;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class LoginOutputModel implements OperationOutput {

    private UserSession userSession;
}
