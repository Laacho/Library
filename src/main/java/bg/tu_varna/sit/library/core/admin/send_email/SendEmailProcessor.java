package bg.tu_varna.sit.library.core.admin.send_email;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationInput;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationModel;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationOutput;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class SendEmailProcessor extends BaseProcessor implements SendEmailOperationModel {
    private static final Logger log = Logger.getLogger(SendEmailProcessor.class);
    @Override
    public Either<Exception, SendEmailOperationOutput> process(SendEmailOperationInput input) {
        return Try.of(()->{
                    log.info("Start sending email");
                    CompletableFuture.runAsync(()->EmailService.sendContactMail(input.getToEmail(),input.getTitle(),input.getBody()));
                    SendEmailOperationOutput output = buildOutput();
                    log.info(output.getMessage());
                    return output;
        }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private SendEmailOperationOutput buildOutput() {
        return SendEmailOperationOutput.builder()
                .message("Successfully sent email")
                .build();
    }
}
