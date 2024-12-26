package bg.tu_varna.sit.library.core.send_verification_code_for_new_email;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailInputModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailOperationModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class SendVerificationCodeForNewEmailProcessor extends BaseProcessor implements SendVerificationCodeForNewEmailOperationModel {

    private static final Logger log = Logger.getLogger(SendVerificationCodeForNewEmailProcessor.class);
    @Override
    public Either<Exception, SendVerificationCodeForNewEmailOutputModel> process(SendVerificationCodeForNewEmailInputModel input) {
        return Try.of(()->{
                    log.info("Started sending verification code for new email");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    String verificationCodeForNewEmail = EmailService.generateVerificationCode();
                    userSession.setNewEmailVerificationCode(verificationCodeForNewEmail);
                    CompletableFuture.runAsync(()->EmailService.sendMail(input.getNewEmail(), verificationCodeForNewEmail));
                    SendVerificationCodeForNewEmailOutputModel output = outputBuilder();
                    log.info("Finished sending verification code for new email");
                    return output;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private  SendVerificationCodeForNewEmailOutputModel outputBuilder() {
        return SendVerificationCodeForNewEmailOutputModel.builder()
                .message("Verification code sent")
                .build();
    }
}
