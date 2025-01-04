package bg.tu_varna.sit.library.core.user.my_profile;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeInputModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOperationModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class SendEmailWithCodeProcessor extends BaseProcessor implements SendEmailWithCodeOperationModel {
    private static final Logger log = Logger.getLogger(SendEmailWithCodeProcessor.class);

    @Override
    public Either<Exception, SendEmailWithCodeOutputModel> process(SendEmailWithCodeInputModel input) {
        return Try.of(()->{
                    log.info("Started sending email with code");
                    validate(input);
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    CompletableFuture.runAsync(()->{EmailService.sendMail(input.getToEmail(),
                            userSession.getVerificationCode());
                    });
                    SendEmailWithCodeOutputModel emailSent =
                            outputBuilder();
                    log.info("Finished sending email with code");
                    return emailSent;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private  SendEmailWithCodeOutputModel outputBuilder() {
        return SendEmailWithCodeOutputModel.builder()
                .message("Email sent")
                .build();
    }
}
