package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.CheckIfReaderProfileExistsProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.GetReaderProfileProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOperationModel;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class GetReaderProfileProcessorTest {
    @Mock
   private ReaderProfileRepository readerProfileRepository;
   @Mock
    private UserCredentialsRepository userCredentialsRepository;
   @Mock
   private BookRepository bookRepository;
    private GetReaderProfileProcessor getReaderProfileProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        getReaderProfileProcessor =SingletonFactory.getSingletonInstance(GetReaderProfileProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(bookRepository);

        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, getReaderProfileProcessor);
        assertInstanceOf(GetReaderProfileOperationModel.class, getReaderProfileProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.getReaderProfileProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
