package bg.tu_varna.sit.library.models.get_all_publishers;

import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetAllPublishersOutputModel implements OperationOutput {
    private List<Publisher> publishers;
}
