package bg.tu_varna.sit.library.models.search;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SearchInputModel implements OperationInput {
  @Size(min = 1,message = "Text must be min value of 1")
  @NotBlank(message = "Text must not be blank")
  private String text;
}
