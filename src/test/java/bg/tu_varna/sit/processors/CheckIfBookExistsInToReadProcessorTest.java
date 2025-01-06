package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.book_data.CheckIfBookExistsInFavoritesProcessor;
import bg.tu_varna.sit.library.core.user.book_data.CheckIfBookExistsInToReadProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_to_read.CheckIfBookExistsInToReadOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class CheckIfBookExistsInToReadProcessorTest {

    @Mock
    private ReaderProfileRepository readerProfileRepository;
    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    @Mock
    private BookRepository bookRepository;

    private CheckIfBookExistsInToReadProcessor checkIfBookExistsInToReadProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        checkIfBookExistsInToReadProcessor =SingletonFactory.getSingletonInstance(CheckIfBookExistsInToReadProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);

        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, checkIfBookExistsInToReadProcessor);
        assertInstanceOf(CheckIfBookExistsInToReadOperationModel.class, checkIfBookExistsInToReadProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.checkIfBookExistsInToReadProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
