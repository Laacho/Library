package bg.tu_varna.sit.library.models.get_all_genres;

import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetAllGenresOutputModel implements OperationOutput{
    private List<Genre> genres;
}
