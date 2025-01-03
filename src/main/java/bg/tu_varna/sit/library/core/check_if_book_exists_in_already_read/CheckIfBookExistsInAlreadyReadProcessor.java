package bg.tu_varna.sit.library.core.check_if_book_exists_in_already_read;

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
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadInputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Set;

@Processor
public class CheckIfBookExistsInAlreadyReadProcessor extends BaseProcessor implements CheckIfBookExistsInAlreadyReadOperationModel {
   private static final Logger log= Logger.getLogger(CheckIfBookExistsInAlreadyReadProcessor.class);
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookRepository bookRepository;
    public CheckIfBookExistsInAlreadyReadProcessor() {
        this.readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }
    @Override
    public Either<Exception, CheckIfBookExistsInAlreadyReadOutputModel> process(CheckIfBookExistsInAlreadyReadInputModel input) {
        return Try.of(()->{
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(userSession.getUsername()).get();
                    ReaderProfile readerProfile = readerProfileRepository.findByUser(userCredentials.getUser())
                            .orElseThrow(() -> new RuntimeException("No reader profile found"));
                            //todo
                    Set<Book> readBooks = readerProfile.getReadBooks();
                    Book book = bookRepository.findByInventoryNumber(input.getCommonBooksProperties().getInventoryNumber())
                            .orElseThrow(() -> new RuntimeException("No book found in inventory"));
                    boolean found = false;
                    for (Book favoriteBook : readBooks) {
                        if(favoriteBook.getInventoryNumber().equals(book.getInventoryNumber())) {
                            found = true;
                            break;
                        }
                    }
                    CheckIfBookExistsInAlreadyReadOutputModel output;
                    if(found) {
                        output= outputBuilder(true);
                    }
                    else{
                        output = outputBuilder(false);
                    }
                    log.info("Finished checking if book exists in already-read");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
    private CheckIfBookExistsInAlreadyReadOutputModel outputBuilder(boolean b) {
        return CheckIfBookExistsInAlreadyReadOutputModel.builder()
                .foundIfExists(b)
                .build();
    }
}
