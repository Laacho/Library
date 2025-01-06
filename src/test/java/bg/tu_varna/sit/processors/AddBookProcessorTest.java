package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.AddBookProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.data.repositories.interfaces.*;
import bg.tu_varna.sit.library.models.add_book.AddBookOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class AddBookProcessorTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private LocationRepository locationRepository;
    private AddBookProcessor addBookProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        addBookProcessor=SingletonFactory.getSingletonInstance(AddBookProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class)).thenReturn(genreRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class)).thenReturn(publisherRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class)).thenReturn(authorRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(LocationRepositoryImpl.class)).thenReturn(locationRepository);
        }
    }


    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,addBookProcessor);
        assertInstanceOf(AddBookOperationModel.class,addBookProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.addBookProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }
}
