package bg.tu_varna.sit.library.core.addBook;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.data.repositories.interfaces.*;
import bg.tu_varna.sit.library.models.addBook.AddBookInputModel;
import bg.tu_varna.sit.library.models.addBook.AddBookOperationModel;
import bg.tu_varna.sit.library.models.addBook.AddBookOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.*;

@Processor
public class AddBookProcessor extends BaseProcessor implements AddBookOperationModel {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final LocationRepository locationRepository;

    private AddBookProcessor() {
        super();
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        this.genreRepository = SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        this.publisherRepository = SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class);
        this.authorRepository = SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class);
        this.locationRepository = SingletonFactory.getSingletonInstance(LocationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AddBookOutputModel> process(AddBookInputModel input) {
        return Try.of(()->{
                    Book.BookBuilder builder = conversionService.convert(input, Book.class).toBuilder();
                    List<Author> all = authorRepository.findAll();
                    if (!all.isEmpty()) {
                        Set<Author> inputAuthors = input.getAuthors();
                        Set<Author> setAuthors = new HashSet<>();
                        for (Author author : inputAuthors) {
                            if(all.contains(author)) {
                                setAuthors.add(author);
                                inputAuthors.remove(author);
                            }
                        }
                        for (Author author : inputAuthors) {
                            Long id = authorRepository.save(author);
                            Author tempAuthor = authorRepository.findById(id).get();
                            setAuthors.add(tempAuthor);
                        }
                        builder.authors(setAuthors);
                    }
                    else{
                        Set<Author> authorsToAdd = input.getAuthors();
                        Set<Author> setAuthors = new HashSet<>();
                        for (Author author : authorsToAdd) {
                            Long id = authorRepository.save(author);
                            Author author1 = authorRepository.findById(id).get();
                            setAuthors.add(author1);
                        }
                        builder.authors(setAuthors);
                    }

                    Optional<Genre> genreByName = genreRepository.findByName(input.getGenre());
                    if(genreByName.isPresent()) {
                        builder.genre(genreByName.get());
                    }
                    else{
                        Genre toSave = Genre.builder()
                                .name(input.getGenre())
                                .build();
                        Long id = genreRepository.save(toSave);
                        Genre byId = genreRepository.findById(id).get();
                        builder.genre(byId);
                    }
                    Optional<Publisher> publisherByName = publisherRepository.findByName(input.getPublisher());
                    if(publisherByName.isPresent()) {
                        builder.publisher(publisherByName.get());
                    }
                    else{
                        Publisher toSave = Publisher.builder()
                                .name(input.getPublisher()).build();
                        Long id = publisherRepository.save(toSave);
                        Publisher byId = publisherRepository.findById(id).get();
                        builder.publisher(byId);
                    }

                    Optional<Location> byNameAndRow = locationRepository.findByNameAndRow(input.getGenre(), input.getRow());
                    if(byNameAndRow.isPresent()) {
                        builder.location(byNameAndRow.get());
                    }
                    else{
                        Location toSave = Location.builder()
                                .shelf(input.getGenre())
                                .rowNum(input.getRow())
                                .build();
                        Long id = locationRepository.save(toSave);
                        Location byId = locationRepository.findById(id).get();
                        builder.location(byId);
                    }
                    Book build = builder.build();
                    bookRepository.save(build);
                    AddBookOutputModel output = AddBookOutputModel.builder().message("Successfully added book").build();
                        return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }


}
