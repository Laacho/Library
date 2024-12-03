package bg.tu_varna.sit.library.models.addGenre;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CheckGenreInputModel implements OperationInput {

    private String genre;
}
