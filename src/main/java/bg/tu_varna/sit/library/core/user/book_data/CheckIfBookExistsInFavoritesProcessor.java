package bg.tu_varna.sit.library.core.user.book_data;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.BookNotFound;
import bg.tu_varna.sit.library.exceptions.ReaderProfileDoesNotExist;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesInputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Set;

@Processor
public class CheckIfBookExistsInFavoritesProcessor extends BaseProcessor implements CheckIfBookExistsInFavoritesOperationModel {
    private static final Logger log = Logger.getLogger(CheckIfBookExistsInFavoritesProcessor.class);
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookRepository bookRepository;

    public CheckIfBookExistsInFavoritesProcessor() {
        this.readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CheckIfBookExistsInFavoritesOutputModel> process(CheckIfBookExistsInFavoritesInputModel input) {
        return Try.of(() -> {
                    log.info("Started check if book exists in favorites");
                    validate(input);
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(userSession.getUsername()).get();
                    ReaderProfile readerProfile = readerProfileRepository.findByUser(userCredentials.getUser())
                            .orElseThrow(() -> new ReaderProfileDoesNotExist("Reader Profile Not Found", "No reader profile found!"));
                    Set<Book> favoriteBooks = readerProfile.getFavoriteBooks();
                    Book book = bookRepository.findByInventoryNumber(input.getCommonBooksProperties().getInventoryNumber())
                            .orElseThrow(() -> new BookNotFound("Book Not Found", "Book with inventory number " + input.getCommonBooksProperties().getInventoryNumber() + " has not been found!"));
                    boolean found = false;
                    for (Book favoriteBook : favoriteBooks) {
                        if (favoriteBook.getInventoryNumber().equals(book.getInventoryNumber())) {
                            found = true;
                            break;
                        }
                    }
                    CheckIfBookExistsInFavoritesOutputModel output = outputBuilder(found);
                    log.info("Finished check if book exists in favorites");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private CheckIfBookExistsInFavoritesOutputModel outputBuilder(boolean b) {
        return CheckIfBookExistsInFavoritesOutputModel.builder()
                .foundIfExists(b)
                .build();
    }
}
