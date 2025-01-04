package bg.tu_varna.sit.library.models.login;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class LoginInputModel implements OperationInput {
    @Size(max = 20,message = "Username too long")
    @NotNull
    private String username;
    @Size(max = 20, message = "Password too long")
    @NotNull
    private String password;
}
