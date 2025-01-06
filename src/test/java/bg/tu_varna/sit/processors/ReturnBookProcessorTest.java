package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.overdue_books.OverdueBooksProcessor;
import bg.tu_varna.sit.library.core.admin.return_books.ReturnBookProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOperationModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ReturnBookProcessorTest {

    @Mock
    private BorrowedBooksRepository borrowedBooksRepository;
    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    private ReturnBookProcessor returnBookProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        returnBookProcessor =SingletonFactory.getSingletonInstance(ReturnBookProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class)).thenReturn(borrowedBooksRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, returnBookProcessor);
        assertInstanceOf(ReturnBooksOperationModel.class, returnBookProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.returnBookProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
