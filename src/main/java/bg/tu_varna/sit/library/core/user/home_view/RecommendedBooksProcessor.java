package bg.tu_varna.sit.library.core.user.home_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksData;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksInputModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOperationModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Processor
public class RecommendedBooksProcessor extends BaseProcessor implements RecommendedBooksOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private final BookRepository bookRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(RecommendedBooksProcessor.class);
    private RecommendedBooksProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, RecommendedBooksOutputModel> process(RecommendedBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started recommended books process");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials user = userCredentialsRepository.findByUsername(userSession.getUsername())
                           .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +userSession.getUsername()+" has not been found"));
                    Optional<ReaderProfile> readerProfile = readerProfileRepository.findByUser(user.getUser());
                    if (readerProfile.isEmpty()) {
                        List<RecommendedBooksData> recommendedBooksData = getRecommendedBooksDataIfUserDoesNotHaveAReaderProfile();
                        log.info("Finished recommended books process with books data");
                        return RecommendedBooksOutputModel.builder().recommendedBooks(recommendedBooksData).build();
                    } else {
                        ReaderProfile readerProfileData = readerProfile.get();
                        Set<Genre> favoriteGenres = readerProfileData.getRecommendedGenres();
                        if (!favoriteGenres.isEmpty()) {
                            List<RecommendedBooksData> recommendedBooksData = getRecommendedBooksDataIfUserHaveAReaderProfile(favoriteGenres);
                            log.info("Finished recommended books process with books data");
                            return RecommendedBooksOutputModel.builder().recommendedBooks(recommendedBooksData).build();
                        } else {
                            List<RecommendedBooksData> recommendedBooks = getRecommendedBooksDataIfUserDoesNotHaveAReaderProfile();
                            log.info("Finished recommended books process");
                            return RecommendedBooksOutputModel.builder().recommendedBooks(recommendedBooks).build();
                        }
                    }
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<RecommendedBooksData> getRecommendedBooksDataIfUserHaveAReaderProfile(Set<Genre> favoriteGenres) {
        List<Genre> genres = new ArrayList<>(favoriteGenres);
        Random random = new Random();
        int indexForGenre = random.nextInt(genres.size());
        Genre genre = genres.get(indexForGenre);
        List<Book> byGenre = bookRepository.findByGenre(genre);
        List<RecommendedBooksData> recommendedBooksData = new ArrayList<>();
        getBooks(byGenre, recommendedBooksData);
        return recommendedBooksData;
    }

    @NotNull
    private List<RecommendedBooksData> getRecommendedBooksDataIfUserDoesNotHaveAReaderProfile() {
        List<Book> allGoodBooks = bookRepository.findAllGoodBooks();
        Collections.shuffle(allGoodBooks);
        List<RecommendedBooksData> recommendedBooksData = new ArrayList<>();
        getBooks(allGoodBooks, recommendedBooksData);
        return recommendedBooksData;
    }

    private void getBooks(List<Book> allGoodBooks, List<RecommendedBooksData> recommendedBooksData) {
        if (allGoodBooks.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                recommendedBooksData.add(conversionService.convert(allGoodBooks.get(i), RecommendedBooksData.class));
            }
        } else {
            for (Book allGoodBook : allGoodBooks) {
                recommendedBooksData.add(conversionService.convert(allGoodBook, RecommendedBooksData.class));
            }
        }
    }
}
