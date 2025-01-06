package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllGenresProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllPublishersProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.PublisherRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.PublisherRepository;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOperationModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class GetAllPublishersProcessorTest {
    @Mock
    private PublisherRepository publisherRepository;

    private GetAllPublishersProcessor getAllPublishersProcessor;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        getAllPublishersProcessor = SingletonFactory.getSingletonInstance(GetAllPublishersProcessor.class);
    }

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class)).thenReturn(publisherRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, getAllPublishersProcessor);
        assertInstanceOf(GetAllPublishersOperationModel.class, getAllPublishersProcessor);
    }

    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.getAllPublishersProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
