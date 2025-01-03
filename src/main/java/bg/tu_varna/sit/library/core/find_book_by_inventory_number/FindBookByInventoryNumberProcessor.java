package bg.tu_varna.sit.library.core.find_book_by_inventory_number;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.exceptions.BookNotFound;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberInputModel;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Optional;

@Processor
public class FindBookByInventoryNumberProcessor extends BaseProcessor implements FindBookByInventoryNumberOperationModel {

    private static final Logger log = Logger.getLogger(FindBookByInventoryNumberProcessor.class);
    private final BookRepository bookRepository;

    public FindBookByInventoryNumberProcessor() {
        super();
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, FindBookByInventoryNumberOutputModel> process(FindBookByInventoryNumberInputModel input) {
        return Try.of(() -> {
                    log.info("Start processing finding book by inventory number");
                    Book byInventoryNumber = bookRepository.findByInventoryNumber(input.getInventoryNumber())
                            .orElseThrow(() -> new BookNotFound("Book Not Found", "Book with inventory number: " + input.getInventoryNumber() + " has not been found"));
                    FindBookByInventoryNumberOutputModel outputModel = FindBookByInventoryNumberOutputModel.builder()
                            .book(byInventoryNumber)
                            .build();
                    log.info("Build output model");
                    return outputModel;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
