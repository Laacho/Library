package bg.tu_varna.sit.library.core.user.search;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.data.repositories.implementations.PublisherRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.PublisherRepository;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersInputModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersOperationModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class GetAllPublishersProcessor extends BaseProcessor implements GetAllPublishersOperationModel {

    private final PublisherRepository publisherRepository;
    private static final Logger log = Logger.getLogger(GetAllPublishersProcessor.class);

    public GetAllPublishersProcessor() {
        this.publisherRepository= SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class);
    }

    @Override
    public Either<Exception, GetAllPublishersOutputModel> process(GetAllPublishersInputModel input) {
        return Try.of(()->{
                log.info("Started getting all publishers");
                    List<Publisher> all = publisherRepository.findAll();
                    GetAllPublishersOutputModel output = GetAllPublishersOutputModel.builder()
                            .publishers(all)
                            .build();
                    log.info("Finished getting all publishers");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
