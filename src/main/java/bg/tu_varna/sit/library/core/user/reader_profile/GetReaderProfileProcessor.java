package bg.tu_varna.sit.library.core.user.reader_profile;

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
import bg.tu_varna.sit.library.exceptions.ReaderProfileDoesNotExist;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.get_reader_profile.BookDataForReader;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileInputModel;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Processor
public class GetReaderProfileProcessor extends BaseProcessor implements GetReaderProfileOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final ReaderProfileRepository readerProfileRepository;
    private final BookRepository bookRepository;
    private static final Logger log= Logger.getLogger(GetReaderProfileProcessor.class);
    private GetReaderProfileProcessor() {
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, GetReaderProfileOutputModel> process(GetReaderProfileInputModel input) {
        return Try.of(() -> {
                    log.info("Started getting reader profile");
                    validate(input);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"));
                    ReaderProfile readerProfile = readerProfileRepository.findByUser(userCredentials.getUser())
                            .orElseThrow(() -> new ReaderProfileDoesNotExist("Reader Profile Not Found","Reader profile for this user has not been found"));
                    List<BookDataForReader> wantsToRead = getBookDataForReader(readerProfile.getWantToRead());
                    List<BookDataForReader> favouriteBooks = getBookDataForReader(readerProfile.getFavoriteBooks());
                    List<BookDataForReader> readBooks = getBookDataForReader(readerProfile.getReadBooks());
                    Map<String, List<BookDataForReader>> genres = getBooksForEveryLikedGenre(readerProfile);
                    GetReaderProfileOutputModel output = buildOutput(readerProfile, wantsToRead, favouriteBooks, readBooks, genres);
                    log.info("Finished getting reader profile");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private GetReaderProfileOutputModel buildOutput(ReaderProfile readerProfile, List<BookDataForReader> wantsToRead, List<BookDataForReader> favouriteBooks, List<BookDataForReader> readBooks, Map<String, List<BookDataForReader>> genres) {
        return GetReaderProfileOutputModel.builder()
                .username(readerProfile.getUser().getUserCredentials().getUsername())
                .wantsToRead(wantsToRead)
                .favouriteBook(favouriteBooks)
                .readBooks(readBooks)
                .recommendedGenres(genres)
                .build();
    }

    @NotNull
    private Map<String, List<BookDataForReader>> getBooksForEveryLikedGenre(ReaderProfile readerProfile) {
        Map<String, List<BookDataForReader>> genres = new HashMap<>();
        for (Genre favoriteGenre : readerProfile.getRecommendedGenres()) {
            List<Book> byGenre = bookRepository.findByGenre(favoriteGenre);
            List<BookDataForReader> genreBooks = getBookDataForReader(new HashSet<>(byGenre));
            genres.put(favoriteGenre.getName(), genreBooks);
        }
        return genres;
    }

    @NotNull
    private List<BookDataForReader> getBookDataForReader(Set<Book> readerProfile) {
        List<BookDataForReader> favouriteBooks = new ArrayList<>();
        for (Book favoriteBook : readerProfile) {
            favouriteBooks.add(conversionService.convert(favoriteBook, BookDataForReader.class));
        }
        return favouriteBooks;
    }

}
