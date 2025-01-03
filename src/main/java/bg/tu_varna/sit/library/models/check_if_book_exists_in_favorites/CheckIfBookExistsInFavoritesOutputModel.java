package bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfBookExistsInFavoritesOutputModel implements OperationOutput {
    private Boolean foundIfExists;
}
