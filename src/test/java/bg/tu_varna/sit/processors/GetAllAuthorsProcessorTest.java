package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.RequestReaderProfileProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllAuthorsProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.AuthorRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOperationModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class GetAllAuthorsProcessorTest {
  @Mock
  private  AuthorRepository authorRepository;
    private GetAllAuthorsProcessor getAllAuthorsProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        getAllAuthorsProcessor =SingletonFactory.getSingletonInstance(GetAllAuthorsProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class)).thenReturn(authorRepository);

        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, getAllAuthorsProcessor);
        assertInstanceOf(GetAllAuthorsOperationModel.class, getAllAuthorsProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.getAllAuthorsProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
