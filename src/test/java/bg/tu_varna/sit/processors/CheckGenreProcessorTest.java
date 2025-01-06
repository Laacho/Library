package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.CheckGenreProcessor;
import bg.tu_varna.sit.library.core.admin.archived_books.ArchivedBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class CheckGenreProcessorTest {
    @Mock
    private  GenreRepository genreRepository;
    @Mock
    private  BookRepository bookRepository;
    private CheckGenreProcessor checkGenreProcessor;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        checkGenreProcessor=SingletonFactory.getSingletonInstance(CheckGenreProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class)).thenReturn(genreRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,checkGenreProcessor);
        assertInstanceOf(CheckGenreOperationModel.class,checkGenreProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.checkGenreProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
