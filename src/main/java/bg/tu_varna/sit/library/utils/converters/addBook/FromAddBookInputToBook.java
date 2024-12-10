package bg.tu_varna.sit.library.utils.converters.addBook;

import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.models.addBook.AddBookInputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = AddBookInputModel.class,to = Book.class)
public class FromAddBookInputToBook implements Converter<AddBookInputModel, Book> {
    @Override
    public Book convert(AddBookInputModel source) {
        return Book.builder()
                .isbn(source.getIsbn())
                .title(source.getTitle())
                .inventoryNumber(source.getInventoryNumber())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .path(source.getPath())
                .build();
    }
}
