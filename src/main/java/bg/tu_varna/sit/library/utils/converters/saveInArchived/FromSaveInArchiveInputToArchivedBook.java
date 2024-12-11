package bg.tu_varna.sit.library.utils.converters.saveInArchived;

import bg.tu_varna.sit.library.data.entities.ArchivedBooks;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedInputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.time.LocalDate;

@Mapper(from=SaveInArchivedInputModel.class, to=ArchivedBooks.class)
public class FromSaveInArchiveInputToArchivedBook implements Converter<SaveInArchivedInputModel, ArchivedBooks> {
    @Override
    public ArchivedBooks convert(SaveInArchivedInputModel source) {
        return ArchivedBooks.builder()
                .archiveDate(LocalDate.now())
                .book(source.getBook())
                .build();
    }
}
