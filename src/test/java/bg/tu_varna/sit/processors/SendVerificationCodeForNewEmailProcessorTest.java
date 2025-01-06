package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.my_profile.SendVerificationCodeForNewEmailProcessor;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOperationModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendVerificationCodeForNewEmailProcessorTest {

    private SendVerificationCodeForNewEmailProcessor sendVerificationCodeForNewEmailProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        sendVerificationCodeForNewEmailProcessor =SingletonFactory.getSingletonInstance(SendVerificationCodeForNewEmailProcessor.class);
    }


    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, sendVerificationCodeForNewEmailProcessor);
        assertInstanceOf(SendVerificationCodeForNewEmailOperationModel.class, sendVerificationCodeForNewEmailProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.sendVerificationCodeForNewEmailProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
