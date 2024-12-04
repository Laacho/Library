package bg.tu_varna.sit.library.models.search;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SearchInputModel implements OperationInput {
  private  String text;
}
