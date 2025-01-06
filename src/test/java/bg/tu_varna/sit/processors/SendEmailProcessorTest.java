package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.search.SearchProcessor;
import bg.tu_varna.sit.library.core.admin.send_email.SendEmailProcessor;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SendEmailProcessorTest {

    private SendEmailProcessor sendEmailProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        sendEmailProcessor =SingletonFactory.getSingletonInstance(SendEmailProcessor.class);
    }


    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, sendEmailProcessor);
        assertInstanceOf(SendEmailOperationModel.class, sendEmailProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.sendEmailProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
