package bg.tu_varna.sit.library.models.search_for_user;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchForUserInputModel implements OperationInput {
    @NotBlank(message = "Title must not be blank")
    @Size(min = 1, message = "Size must be min 1")
    private String title;
    private String filterAuthor;
    private String filterPublisher;
    private String filterGenre;
}
