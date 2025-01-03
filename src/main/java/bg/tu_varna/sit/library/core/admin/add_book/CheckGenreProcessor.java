package bg.tu_varna.sit.library.core.admin.add_book;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.LocationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreInputModel;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Processor
public class CheckGenreProcessor extends BaseProcessor implements CheckGenreOperationModel {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final int MAX_BOOKS_PER_ROW = 20;
    private static final Logger log=Logger.getLogger(CheckGenreProcessor.class);
    private CheckGenreProcessor() {
        this.genreRepository = SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CheckGenreOutputModel> process(CheckGenreInputModel input) {
        return Try.of(() -> {
                    log.info("Started checking genre");
                    Optional<Genre> byName = genreRepository.findByName(input.getGenre().toLowerCase());
                    if (byName.isPresent()) {
                        //ima go i trqbva da se vurnat vsichki knigi
                        List<Book> byGenre = bookRepository.findByGenre(byName.get());
                        if (!byGenre.isEmpty()) {
                            //rowNum- kolko puti se e sreshtalo
                            Map<Long, Integer> rowToCount = getLongIntegerMap(byGenre);
                            List<Long> result = getResult(rowToCount);
                            CheckGenreOutputModel output = CheckGenreOutputModel.builder()
                                    .message("Successfully founded empty rows")
                                    .rowNums(result)
                                    .isGenrePresent(true)
                                    .build();
                            log.info("Finished checking genre");
                            return output;
                        } else {
                            return buildOutputForNotFoundGenreOrBooksNotFound(input, true);
                        }
                    } else {
                        // nqma go i trqbva da se dobavi genre
                        return buildOutputForNotFoundGenreOrBooksNotFound(input, false);
                    }
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<Long> getResult(Map<Long, Integer> rowToCount) {
        List<Long> result = new ArrayList<>();
        for (Map.Entry<Long, Integer> kvp : rowToCount.entrySet()) {
            if (kvp.getValue() >= MAX_BOOKS_PER_ROW) {
               continue;
            }
            //rowNUm na shelf
            result.add(kvp.getKey());
        }
        return result;
    }

    private CheckGenreOutputModel buildOutputForNotFoundGenreOrBooksNotFound(CheckGenreInputModel input, Boolean isGenreNotFound) {
        return CheckGenreOutputModel.builder()
                .message("Successfully added genre")
                .rowNums(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))
                .isGenrePresent(isGenreNotFound)
                .build();
    }

    private Map<Long, Integer> getLongIntegerMap(List<Book> byGenre) {
        Map<Long, Integer> rowToCount = initMap();
        for (Book book : byGenre) {
            Long rowNum = book.getLocation().getRowNum();
            rowToCount.put(rowNum, rowToCount.get(rowNum) + 1);
        }
        return rowToCount;
    }

    @NotNull
    private Map<Long, Integer> initMap() {
        Map<Long, Integer> rowToCount = new HashMap<>();
        for (long i = 0; i < 10; i++) {
            rowToCount.put(i + 1, 0);
        }
        return rowToCount;
    }
}
