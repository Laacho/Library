package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.AddBookProcessor;
import bg.tu_varna.sit.library.core.admin.approve_books.ApproveBooksProcessor;
import bg.tu_varna.sit.library.core.admin.home_view.AdminHomeViewProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOperationModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ApproveBooksProcessorTest {
    @Mock
    private BorrowedBooksRepository borrowedBooksRepository;
    @Mock
    private BookRepository bookRepository;

    private ApproveBooksProcessor approveBooksProcessor;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
     approveBooksProcessor=SingletonFactory.getSingletonInstance(ApproveBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class)).thenReturn(borrowedBooksRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,approveBooksProcessor);
        assertInstanceOf(ApproveBooksOperationModel.class,approveBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.approveBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
