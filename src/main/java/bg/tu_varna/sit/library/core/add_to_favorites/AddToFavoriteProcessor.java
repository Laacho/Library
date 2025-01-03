package bg.tu_varna.sit.library.core.add_to_favorites;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteInputModel;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteOperationModel;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.util.Set;

@Processor
public class AddToFavoriteProcessor extends BaseProcessor implements AddToFavoriteOperationModel {

    private static final Logger log = Logger.getLogger(AddToFavoriteProcessor.class);
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookRepository bookRepository;

    public AddToFavoriteProcessor() {
        this.readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AddToFavoriteOutputModel> process(AddToFavoriteInputModel input) {
        return Try.of(()->{
                log.info("Started processing adding to favorite");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    String username = userSession.getUsername();
                    UserCredentials loggedUser = userCredentialsRepository.findByUsername(username).get();

                    ReaderProfile readerProfile = readerProfileRepository.
                            findByUser(loggedUser.getUser())
                            .orElseThrow(() -> new RuntimeException("No reader profile found!"));
                    Set<Book> favoriteBooks = readerProfile.getFavoriteBooks();
                    Book book = bookRepository.findByInventoryNumber(input.getCommonBooksProperties()
                                    .getInventoryNumber())
                            .orElseThrow(() -> new RuntimeException("No book found!"));
                    //todo
                    Set<Genre> recommendedGenres = readerProfile.getRecommendedGenres();
                    if(input.getWantsToDelete()) {
                        favoriteBooks.removeIf(favoriteBook -> favoriteBook.getInventoryNumber()
                                .equals(input.getCommonBooksProperties()
                                        .getInventoryNumber()));

                        recommendedGenres.removeIf(genre -> genre.getName()
                                                                    .equals(input
                                                                            .getCommonBooksProperties()
                                                                            .getGenre()));
                    }
                    else{
                        favoriteBooks.add(book);

                        recommendedGenres.add(book.getGenre());
                    }
                    readerProfile.setFavoriteBooks(favoriteBooks);
                    readerProfileRepository.update(readerProfile);
                    AddToFavoriteOutputModel output = outputBuilder();
                    log.info("Finished processing adding/removing from favorite");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private AddToFavoriteOutputModel outputBuilder() {
        return AddToFavoriteOutputModel.builder()
                .message("Successfully added/removed to favorite")
                .build();
    }
}
