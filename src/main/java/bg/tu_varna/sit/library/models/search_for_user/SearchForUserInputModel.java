package bg.tu_varna.sit.library.models.search_for_user;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchForUserInputModel implements OperationInput {
    @NotBlank(message = "Title must not be blank")
    @Size(min = 1,message = "Size must be min 1")
    private String title;

    @NotBlank(message = "Author filter must not be blank")
    @Size(min = 2, max = 50, message = "Author filter must be between 2 and 50 characters")
    private String filterAuthor;

    @NotBlank(message = "Publisher filter must not be blank")
    @Size(min = 2, max = 50, message = "Publisher filter must be between 2 and 50 characters")
    private String filterPublisher;

    @NotBlank(message = "Genre filter must not be blank")
    @Size(min = 2, max = 50, message = "Genre filter must be between 2 and 50 characters")
    private String filterGenre;
}
