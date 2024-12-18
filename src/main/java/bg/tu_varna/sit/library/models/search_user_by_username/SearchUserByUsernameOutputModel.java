package bg.tu_varna.sit.library.models.search_user_by_username;

import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserByUsernameOutputModel implements OperationOutput {
    private UserCredentials userCredentials;
}
