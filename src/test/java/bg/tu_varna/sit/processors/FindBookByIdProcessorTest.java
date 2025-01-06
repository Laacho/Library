package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.borrow_cart.BorrowBooksProcessor;
import bg.tu_varna.sit.library.core.user.home_view.FindBookByIdProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class FindBookByIdProcessorTest {
    @Mock
    private BookRepository bookRepository;

    private FindBookByIdProcessor borrowBooksProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        borrowBooksProcessor =SingletonFactory.getSingletonInstance(FindBookByIdProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, borrowBooksProcessor);
        assertInstanceOf(FindBookByIdOperationModel.class, borrowBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.borrowBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
