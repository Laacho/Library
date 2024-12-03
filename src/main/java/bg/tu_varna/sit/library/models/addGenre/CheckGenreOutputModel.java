package bg.tu_varna.sit.library.models.addGenre;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CheckGenreOutputModel implements OperationOutput {
    private String message;
    private List<Long> rowNums;
    private Boolean isGenrePresent;

}
