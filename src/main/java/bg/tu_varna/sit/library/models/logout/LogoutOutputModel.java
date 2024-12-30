package bg.tu_varna.sit.library.models.logout;

import bg.tu_varna.sit.library.models.base.OperationOutput;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LogoutOutputModel implements OperationOutput {
    private String message;
}
