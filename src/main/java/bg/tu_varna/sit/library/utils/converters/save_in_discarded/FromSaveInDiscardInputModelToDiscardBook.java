package bg.tu_varna.sit.library.utils.converters.save_in_discarded;

import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardInputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.time.LocalDate;

@Mapper(from = SaveToDiscardInputModel.class, to = DiscardedBooks.class)
public class FromSaveInDiscardInputModelToDiscardBook implements Converter<SaveToDiscardInputModel, DiscardedBooks> {
    @Override
    public DiscardedBooks convert(SaveToDiscardInputModel source) {
        return DiscardedBooks.builder()
                .book(source.getBook())
                .discardingDate(LocalDate.now())
                .reason(source.getReason())
                .build();
    }
}
