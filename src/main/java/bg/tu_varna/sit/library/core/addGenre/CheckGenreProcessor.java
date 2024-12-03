package bg.tu_varna.sit.library.core.addGenre;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.LocationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreInputModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.*;

@Processor
public class CheckGenreProcessor extends BaseProcessor implements CheckGenreOperationModel {
    private final GenreRepository genreRepository;
    private final LocationRepository locationRepository;
    private final BookRepository bookRepository;

    private CheckGenreProcessor() {
        this.genreRepository = SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        this.locationRepository = SingletonFactory.getSingletonInstance(LocationRepositoryImpl.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CheckGenreOutputModel> process(CheckGenreInputModel input) {
        return Try.of(()->{
                    Optional<Genre> byName = genreRepository.findByName(input.getGenre());
                    if(byName.isPresent()) {
                        //ima go i trqbva da se vurnat vsichki knigi
                        List<Book> byGenre = bookRepository.findByGenre(byName.get());
                        if(!byGenre.isEmpty()) {
                            //rowNum- kolko puti se e sreshtalo
                            Map<Long, Integer> rowToCount = getLongIntegerMap(byGenre);
                            List<Long> result = new ArrayList<>();
                            for (Map.Entry<Long, Integer> kvp: rowToCount.entrySet()) {
                                if(kvp.getValue()<20){
                                   result.add(kvp.getKey());
                                   //rowNUm na shelf
                                }
                            }
                            CheckGenreOutputModel output = CheckGenreOutputModel.builder()
                                    .message("Successfully founded empty rows")
                                    .rowNums(result)
                                    .isGenrePresent(true)
                                    .build();
                            //log
                            return output;
                        }
                        else{
                            return buildOutputForNotFoundGenreOrBooksNotFound(input,true);
                        }
                    }
                    else{
                        // nqma go i trqbva da se dobavi genre
                        return buildOutputForNotFoundGenreOrBooksNotFound(input,false);
                    }
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private CheckGenreOutputModel buildOutputForNotFoundGenreOrBooksNotFound(CheckGenreInputModel input,Boolean isGenreNotFound) {
        CheckGenreOutputModel output = CheckGenreOutputModel.builder()
                .message("Successfully added genre")
                .rowNums(List.of(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L))
                .isGenrePresent(isGenreNotFound)
                .build();
        return output;
    }

    private static Map<Long, Integer> getLongIntegerMap(List<Book> byGenre) {
        Map<Long,Integer> rowToCount = new HashMap<>();
        for (Book book : byGenre) {
            Long rowNum = book.getLocation().getRowNum();
            if(rowToCount.containsKey(rowNum)) {
                rowToCount.put(rowNum, rowToCount.get(rowNum)+1);
            }
            else{
                rowToCount.put(rowNum, 1);
            }
        }
        return rowToCount;
    }
}
