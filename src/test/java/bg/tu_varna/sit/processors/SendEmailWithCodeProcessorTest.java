package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.my_profile.SendEmailWithCodeProcessor;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOperationModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SendEmailWithCodeProcessorTest {

    private SendEmailWithCodeProcessor sendEmailWithCodeProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        sendEmailWithCodeProcessor =SingletonFactory.getSingletonInstance(SendEmailWithCodeProcessor.class);
    }


    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, sendEmailWithCodeProcessor);
        assertInstanceOf(SendEmailWithCodeOperationModel.class, sendEmailWithCodeProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.sendEmailWithCodeProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
