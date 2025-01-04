package bg.tu_varna.sit.library.models.reader_profile_table_view;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ReaderProfileTableViewOutputModel implements OperationOutput {
    private List<ReaderProfileData> readerProfileData;
}
