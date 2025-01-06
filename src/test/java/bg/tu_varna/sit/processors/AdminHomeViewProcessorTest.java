package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.AddBookProcessor;
import bg.tu_varna.sit.library.core.admin.home_view.AdminHomeViewProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.data.repositories.interfaces.*;
import bg.tu_varna.sit.library.models.add_book.AddBookOperationModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.SingleTestExecutor;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class AdminHomeViewProcessorTest {

    @Mock
    private DiscardedBooksRepository discardedBooksRepository;
    @Mock
   private UserCredentialsRepository userCredentialsRepository;
    @Mock
   private ArchivedRepository archivedRepository;
    @Mock
   private ReaderProfileRepository readerProfileRepository;
    @Mock
   private BookRepository bookRepository;
    @Mock
   private NotificationRepository notificationRepository;
    private AdminHomeViewProcessor adminHomeViewProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        adminHomeViewProcessor=SingletonFactory.getSingletonInstance(AdminHomeViewProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class)).thenReturn(discardedBooksRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class)).thenReturn(archivedRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class)).thenReturn(notificationRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,adminHomeViewProcessor);
        assertInstanceOf(AdminHomeViewOperationModel.class,adminHomeViewProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.adminHomeViewProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
