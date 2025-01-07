package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.all_books.AllBooksProcessor;
import bg.tu_varna.sit.library.core.user.book_data.*;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadInputModel;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadOperationModel;
import bg.tu_varna.sit.library.models.add_to_already_read.AddToAlreadyReadOutputModel;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteInputModel;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteOperationModel;
import bg.tu_varna.sit.library.models.add_to_favorites.AddToFavoriteOutputModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadInputModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadOperationModel;
import bg.tu_varna.sit.library.models.add_to_read.AddToReadOutputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksInputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOperationModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOutputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadInputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_already_read.CheckIfBookExistsInAlreadyReadOutputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesInputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites.CheckIfBookExistsInFavoritesOutputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_to_read.CheckIfBookExistsInToReadInputModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_to_read.CheckIfBookExistsInToReadOperationModel;
import bg.tu_varna.sit.library.models.check_if_book_exists_in_to_read.CheckIfBookExistsInToReadOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class BookDataController extends UserController implements Initializable {
    public static CommonBooksProperties booksData;
    @FXML
    public ImageView imageViewAlreadyRead;
    @FXML
    public ImageView imageViewWantToRead;
    @FXML
    public ImageView imageViewFavourite;
    @FXML
    public ImageView imageViewCart;
    @FXML
    private Label title;
    @FXML
    private Label publisher;
    @FXML
    private Label isbn;
    @FXML
    private Label inventoryNumber;
    @FXML
    private Label authors;
    @FXML
    private Label genre;
    @FXML
    private ImageView imageView;
    @FXML
    private Button favorite;
    @FXML
    private Button addToRead;
    @FXML
    private Button addToAlreadyRead;
    private Boolean alreadyExitsInFavorites;
    private Boolean alreadyExistsInAlreadyRead;
    private Boolean alreadyExitsInRead;
    private final AddToFavoriteOperationModel addToFavoriteOperationModel;
    private final CheckIfBookExistsInFavoritesOperationModel checkIfBookExistsInFavoritesOperationModel;
    private final CheckIfBookExistsInToReadOperationModel checkIfBookExistsInToReadOperationModel;
    private final AddToReadOperationModel addToReadOperationModel;
    private final AddToAlreadyReadOperationModel addToAlreadyReadOperationModel;
    private final CheckIfBookExistsInAlreadyReadOperationModel checkIfBookExistsInAlreadyReadOperationModel;
    private final AllBooksOperationModel allBooksProcessor;
    private final ConversionService conversionService;

    public BookDataController() {
        this.checkIfBookExistsInToReadOperationModel = SingletonFactory.getSingletonInstance(CheckIfBookExistsInToReadProcessor.class);
        this.addToFavoriteOperationModel = SingletonFactory.getSingletonInstance(AddToFavoriteProcessor.class);
        this.checkIfBookExistsInFavoritesOperationModel = SingletonFactory.getSingletonInstance(CheckIfBookExistsInFavoritesProcessor.class);
        this.addToReadOperationModel = SingletonFactory.getSingletonInstance(AddToReadProcessor.class);
        this.addToAlreadyReadOperationModel = SingletonFactory.getSingletonInstance(AddToAlreadyReadProcessor.class);
        this.checkIfBookExistsInAlreadyReadOperationModel = SingletonFactory.getSingletonInstance(CheckIfBookExistsInAlreadyReadProcessor.class);
        alreadyExitsInFavorites = false;
        alreadyExitsInRead = false;
        alreadyExistsInAlreadyRead = false;
        allBooksProcessor = SingletonFactory.getSingletonInstance(AllBooksProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }

    public void change() {
        title.setText(title.getText() + " " + booksData.getTitle());
        publisher.setText(publisher.getText() + " " + booksData.getPublisher());
        authors.setText(authors.getText() + " " + String.join(", ", booksData.getAuthors()));
        genre.setText(genre.getText() + " " + booksData.getGenre());
        File file = new File(booksData.getPathImage());
        imageView.setImage(new Image(file.toURI().toString()));
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);
    }

    @FXML
    public void addToCart(ActionEvent actionEvent) {
        Either<Exception, AllBooksOutputModel> process = allBooksProcessor.process(AllBooksInputModel.builder().build());
        boolean toAdd = true;
        if (process.isRight()) {
            UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
            List<BooksData> allBooks = process.get().getBooksData();
            BooksData saved = null;
            for (BooksData allBook : allBooks) {
                for (Book cartBook : userSession.getCartBooks()) {
                    if (cartBook.getInventoryNumber().equals(allBook.getInventoryNumber())
                            && cartBook.getInventoryNumber().equals(booksData.getInventoryNumber())) {
                        toAdd = false;
                    }
                }
                if(allBook.getInventoryNumber().equals(booksData.getInventoryNumber())){
                    saved = allBook;
                }
            }
            if (toAdd && saved!=null)
                userSession.getCartBooks().add(conversionService.convert(saved, Book.class));
            toAdd = false;
            for (CommonBooksProperties borrowedBook : BorrowCartController.getBorrowedBooks()) {
                if (borrowedBook.getInventoryNumber().equals(booksData.getInventoryNumber())) {
                    toAdd = false;
                }
            }
            if (toAdd)
                BorrowCartController.addBookInSet(booksData);
        }

    }

    @FXML
    public void addToFavorites(ActionEvent event) {
        if (!alreadyExitsInFavorites) {
            AddToFavoriteInputModel input = AddToFavoriteInputModel.builder()
                    .commonBooksProperties(booksData)
                    .wantsToDelete(false)
                    .build();
            processInputForFavorite(input);

        } else {
            AddToFavoriteInputModel input = AddToFavoriteInputModel.builder()
                    .commonBooksProperties(booksData)
                    .wantsToDelete(true)
                    .build();
            processInputForFavorite(input);
        }
        updateFavoriteButtonState();
    }

    private void processInputForFavorite(AddToFavoriteInputModel input) {
        Either<Exception, AddToFavoriteOutputModel> process = addToFavoriteOperationModel.process(input);
        if (process.isRight() && alreadyExitsInFavorites) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully removed from favourite", ButtonType.OK);
            alreadyExitsInFavorites = false;
        } else if (process.isRight() && !alreadyExitsInFavorites) {
            alreadyExitsInFavorites = true;
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully added in favourite", ButtonType.OK);
        }else if (process.isLeft()) {
            alreadyExitsInFavorites = false;
        }
    }

    @FXML
    public void addToRead(ActionEvent event) {
        if (!alreadyExitsInRead) {
            AddToReadInputModel input = AddToReadInputModel
                    .builder()
                    .commonBooksProperties(booksData)
                    .wantsToDelete(false)
                    .build();
            processInputForToRead(input);
        } else {
            AddToReadInputModel input = AddToReadInputModel
                    .builder()
                    .commonBooksProperties(booksData)
                    .wantsToDelete(true)
                    .build();
            processInputForToRead(input);
        }
        updateToReadButtonState();
    }

    private void processInputForToRead(AddToReadInputModel input) {
        Either<Exception, AddToReadOutputModel> process = addToReadOperationModel.process(input);
        if (process.isRight() && alreadyExitsInRead) {
            alreadyExitsInRead = false;
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully removed from wants to read", ButtonType.OK);
        } else if (process.isRight() && !alreadyExitsInRead) {
            alreadyExitsInRead = true;
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully added in wants to read", ButtonType.OK);
        }else if (process.isLeft()) {
            alreadyExitsInRead = false;
        }

    }

    @FXML
    public void addToAlreadyRead(ActionEvent event) {
        if (!alreadyExistsInAlreadyRead) {
            AddToAlreadyReadInputModel input = AddToAlreadyReadInputModel.builder()
                    .commonBooksProperties(booksData)
                    .wantToDelete(false)
                    .build();
            processInputForAlreadyRead(input);
        } else {
            AddToAlreadyReadInputModel input = AddToAlreadyReadInputModel.builder()
                    .commonBooksProperties(booksData)
                    .wantToDelete(true)
                    .build();
            processInputForAlreadyRead(input);
        }
        updateAddToAlreadyReadButtonState();

    }

    private void processInputForAlreadyRead(AddToAlreadyReadInputModel input) {
        Either<Exception, AddToAlreadyReadOutputModel> process = addToAlreadyReadOperationModel.process(input);
        if (process.isRight() && alreadyExistsInAlreadyRead) {
            alreadyExistsInAlreadyRead = false;
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully removed from already read", ButtonType.OK);
        } else if (process.isRight() && !alreadyExistsInAlreadyRead) {
            alreadyExistsInAlreadyRead = true;
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success!", "Successfully added to already read", ButtonType.OK);
        }else if (process.isLeft()) {
            alreadyExistsInAlreadyRead = false;
        }
    }

    private void updateAddToAlreadyReadButtonState() {
        if (alreadyExistsInAlreadyRead) {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/to-already-read-.png");
            imageViewAlreadyRead.setImage(new Image(file.toURI().toString()));
        } else {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/remove-already-read-.png");
            imageViewAlreadyRead.setImage(new Image(file.toURI().toString()));
        }
    }

    private void updateToReadButtonState() {
        if (alreadyExitsInRead) {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/want-to-read.png");
            imageViewWantToRead.setImage(new Image(file.toURI().toString()));
        } else {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/remove-wishlist-.png");
            imageViewWantToRead.setImage(new Image(file.toURI().toString()));
        }
    }

    private void updateFavoriteButtonState() {
        if (alreadyExitsInFavorites) {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/to-heart.png");
            imageViewFavourite.setImage(new Image(file.toURI().toString()));
        } else {
            File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/images/remove-heart-png.png");
            imageViewFavourite.setImage(new Image(file.toURI().toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CompletableFuture.runAsync(() -> {
            CheckIfBookExistsInFavoritesInputModel input = CheckIfBookExistsInFavoritesInputModel.builder()
                    .commonBooksProperties(booksData)
                    .build();
            Either<Exception, CheckIfBookExistsInFavoritesOutputModel> process = checkIfBookExistsInFavoritesOperationModel.process(input);
            if (process.isRight()) {
                this.alreadyExitsInFavorites = process.get().getFoundIfExists();
                Platform.runLater(this::updateFavoriteButtonState);
            }
        });

        CompletableFuture.runAsync(() -> {
            CheckIfBookExistsInToReadInputModel input2 = CheckIfBookExistsInToReadInputModel.builder()
                    .commonBooksProperties(booksData)
                    .build();
            Either<Exception, CheckIfBookExistsInToReadOutputModel> process1 = checkIfBookExistsInToReadOperationModel.process(input2);
            if (process1.isRight()) {
                this.alreadyExitsInRead = process1.get().getFoundIfExists();
                Platform.runLater(this::updateToReadButtonState);
            }
        });

        CompletableFuture.runAsync(() -> {
            CheckIfBookExistsInAlreadyReadInputModel input3 = CheckIfBookExistsInAlreadyReadInputModel.builder()
                    .commonBooksProperties(booksData)
                    .build();
            Either<Exception, CheckIfBookExistsInAlreadyReadOutputModel> process2 = checkIfBookExistsInAlreadyReadOperationModel.process(input3);
            if (process2.isRight()) {
                this.alreadyExistsInAlreadyRead = process2.get().getFoundIfExists();
                Platform.runLater(this::updateAddToAlreadyReadButtonState);
            }
        });

    }
}
