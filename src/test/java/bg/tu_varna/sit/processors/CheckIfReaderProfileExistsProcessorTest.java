package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.CheckIfReaderProfileExistsProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class CheckIfReaderProfileExistsProcessorTest {
    @Mock
   private ReaderProfileRepository readerProfileRepository;
   @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private CheckIfReaderProfileExistsProcessor checkIfReaderProfileExistsProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        checkIfReaderProfileExistsProcessor =SingletonFactory.getSingletonInstance(CheckIfReaderProfileExistsProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, checkIfReaderProfileExistsProcessor);
        assertInstanceOf(CheckIfReaderProfileExistsOperationModel.class, checkIfReaderProfileExistsProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.checkIfReaderProfileExistsProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
