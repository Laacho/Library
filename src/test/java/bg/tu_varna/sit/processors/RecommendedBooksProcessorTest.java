package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.home_view.NewBooksProcessor;
import bg.tu_varna.sit.library.core.user.home_view.RecommendedBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.new_books.NewBooksOperationModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class RecommendedBooksProcessorTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReaderProfileRepository readerProfileRepository;
    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private RecommendedBooksProcessor recommendedBooksProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        recommendedBooksProcessor =SingletonFactory.getSingletonInstance(RecommendedBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, recommendedBooksProcessor);
        assertInstanceOf(RecommendedBooksOperationModel.class, recommendedBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.recommendedBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
