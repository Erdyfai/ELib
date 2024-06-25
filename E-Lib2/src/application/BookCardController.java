package application;

import dto.BookDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BookCardController {

    @FXML
    private Label bookCategory_card;

    @FXML
    private ImageView bookCover_card;

    @FXML
    private Label bookTitle_card;

    @FXML
    private Label bookWriter_card;

    @FXML
    private VBox box;
    
    private BookDTO book;
    
    private StudentController studentController;
    
    public void setData(BookDTO book, StudentController studentController) {
    	this.studentController = studentController;
    	this.book = book;
    	Image image = new Image(getClass().getResourceAsStream(book.getImagePath()));
    	bookCover_card.setImage(image);
    	bookTitle_card.setText(book.getTitle());
    	bookWriter_card.setText(book.getWriter());
    	bookCategory_card.setText(book.getCategory());
    }
    
    @FXML
    private void handleCardClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Handle double-click event
            studentController.openOrderForm(book);
        }
    }
    
    
}
