package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.overdue_books.OverdueBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class OverdueBooksProcessorTest {

    @Mock
    private BorrowedBooksRepository borrowedBooksRepository;

    private OverdueBooksProcessor discardedBooksProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        discardedBooksProcessor=SingletonFactory.getSingletonInstance(OverdueBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class)).thenReturn(borrowedBooksRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,discardedBooksProcessor);
        assertInstanceOf(OverdueBooksOperationModel.class,discardedBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.discardedBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
