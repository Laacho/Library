package bg.tu_varna.sit.library.models.search_for_user;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchForUserInputModel implements OperationInput {
    private String title;
    private String filterAuthor;
    private String filterPublisher;
    private String filterGenre;
    private Double filterMinPrice;
    private Double filterMaxPrice;
}
