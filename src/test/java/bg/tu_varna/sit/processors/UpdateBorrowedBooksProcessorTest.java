package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.approve_books.UpdateBorrowedBooksProcessor;
import bg.tu_varna.sit.library.core.admin.discarded_books.DiscardedBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksOperationModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class UpdateBorrowedBooksProcessorTest {
    @Mock
    private  BorrowedBooksRepository borrowedBooksRepository;
    @Mock
    private  NotificationRepository notificationRepository;
    private UpdateBorrowedBooksProcessor updateBorrowedBooksProcessor;
    @BeforeEach
    void setUp()throws Exception {
        SingletonFactory.init();
        updateBorrowedBooksProcessor=SingletonFactory.getSingletonInstance(UpdateBorrowedBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class)).thenReturn(borrowedBooksRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(notificationRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,updateBorrowedBooksProcessor);
        assertInstanceOf(UpdateBorrowedBooksOperationModel.class,updateBorrowedBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean annotationPresent = this.updateBorrowedBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(annotationPresent);
    }
}
