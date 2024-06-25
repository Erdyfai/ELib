package application;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import dao.BookDAO;
import dao.BorrowedBookDAO;
import dto.BookDTO;
import dto.BorrowedBookDTO;
import dto.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentController implements Initializable {

	@FXML
    private Button applyButton_extendForm;

    @FXML
    private Button applyButton_orderForm;

    @FXML
    private HBox bookCard_extendForm;

    @FXML
    private VBox bookCard_orderForm;

    @FXML
    private GridPane bookContainer;

    @FXML
    private Label bookTitle_extendForm;

    @FXML
    private Label bookTitle_orderForm;

    @FXML
    private Button browse_menuButton;

    @FXML
    private Button cancelButton_extendForm;

    @FXML
    private Button cancelButton_orderForm;

    @FXML
    private HBox cardLayout;

    @FXML
    private Label date_orderForm;

    @FXML
    private VBox details_extendForm;

    @FXML
    private VBox details_orderForm;

    @FXML
    private Button doneButton_extendForm;

    @FXML
    private Button doneButton_orderForm;

    @FXML
    private Label durationAfter_extendForm;

    @FXML
    private Label durationBefore_extendForm;

    @FXML
    private ComboBox<Integer> durationCbox_extendForm;

    @FXML
    private ComboBox<Integer> durationCbox_orderForm;

    @FXML
    private AnchorPane extensionForm;

    @FXML
    private Button extension_menuButton;

    @FXML
    private Label message_extendForm;

    @FXML
    private Label message_orderForm;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private Button order_menuButton;

    @FXML
    private Button profile_menuButton;
    
    private BookDAO bookDAO = new BookDAO();
    private BookDTO selectedBookOrder;
    
    
	private BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();
	private BorrowedBookDTO selectedBorrowedBook;
	
	private List<BorrowedBookDTO> borrowedBookList = borrowedBookDAO.selectAllBorrowedBooks();
	private List<BookDTO> bookList = bookDAO.selectAllBooks();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		durationCbox_orderForm.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7));
		durationCbox_extendForm.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7));
		loadCard();
	}
	
	private void loadCard() {
		int column = 0;
		int row = 1;
		try {
			for (BorrowedBookDTO borrowedBookDTO: borrowedBookList) {
				BookDTO bookDTO = bookDAO.selectBookById(borrowedBookDTO.getBookId());
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/fxml/borrowedBooks_card.fxml"));
				HBox cardBox = fxmlLoader.load();
				BorrowedBookCardController cardController = fxmlLoader.getController();
				cardController.setData(bookDTO, borrowedBookDTO, this);
				cardLayout.getChildren().add(cardBox);
			}
			for (BookDTO bookDTO : bookList) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/fxml/book_card.fxml"));
				VBox bookBox = fxmlLoader.load();
				BookCardController bookController = fxmlLoader.getController();
				bookController.setData(bookDTO, this);
				
				if(column == 6) {
					column = 0;
					++row;
				}
				
				bookContainer.add(bookBox, column++, row);
				GridPane.setMargin(bookBox, new Insets(10));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void switchForm(ActionEvent event) {
		if(event.getSource() == profile_menuButton) {
			
		}else if(event.getSource() == browse_menuButton) {
			orderForm.setVisible(false);
			extensionForm.setVisible(false);
			
			
		}else if(event.getSource() == order_menuButton) {
			orderForm.setVisible(true);
			extensionForm.setVisible(false);
			message_orderForm.setVisible(true);
			
		}else if(event.getSource() == extension_menuButton) {
			orderForm.setVisible(false);
			extensionForm.setVisible(true);
			message_extendForm.setVisible(true);
		}
	}
	
//=== ORDER BOOK FORM ===
	@FXML
	public void orderBooks(ActionEvent event) {
		if(event.getSource() == applyButton_orderForm) {
			 if (durationCbox_orderForm.getValue() != null) {
		            LocalDate now = LocalDate.now();
		            java.sql.Date sqlDate = java.sql.Date.valueOf(now);
		            
		            details_orderForm.setVisible(true);
		            //setDisableOrderFormButton(true);
					bookTitle_orderForm.setText(selectedBookOrder.getTitle());
					date_orderForm.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(now));
					borrowedBookDAO.addBorrowedBook(SessionManager.getLoggedInUser().getId(),selectedBookOrder.getBookId(),durationCbox_orderForm.getValue(),sqlDate,null,"booked");
		        }
		}else if(event.getSource() == cancelButton_orderForm) {
			orderForm.setVisible(false);
			emptyBookCard();
		}else if(event.getSource() == doneButton_orderForm) {
			loadCard();
			orderForm.setVisible(false);
			details_orderForm.setVisible(false);
			emptyBookCard();
		}
	}
	
	public void openOrderForm(BookDTO selectedBook) {
		this.selectedBookOrder = selectedBook;
		orderForm.setVisible(true);
		message_orderForm.setVisible(false);
		emptyBookCard();
		
		VBox cardBox = null;
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/fxml/book_card.fxml"));
		try {
			cardBox = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BookCardController bookController = fxmlLoader.getController();
		bookController.setData(selectedBook, this);
		bookCard_orderForm.getChildren().add(cardBox);
	}
	
	private void emptyBookCard(){
		if(!bookCard_orderForm.getChildren().isEmpty()) {
			bookCard_orderForm.getChildren().remove(0);
			message_orderForm.setVisible(true);
		}
	}
	
//	private void setDisableOrderFormButton(boolean con) {
//		applyButton_orderForm.setDisable(con);
//		cancelButton_orderForm.setDisable(con);
//	}

	
//=== EXTEND BOOK FORM ===
	@FXML
	public void extendBook(ActionEvent event) {
		if(event.getSource() == applyButton_extendForm) {
			if(durationCbox_extendForm.getValue() != null) {
				details_extendForm.setVisible(true);
				durationBefore_extendForm.setText(String.valueOf(selectedBorrowedBook.getBorrowDuration()));
				durationAfter_extendForm.setText(String.valueOf(selectedBorrowedBook.getBorrowDuration() + durationCbox_extendForm.getValue()));
				borrowedBookDAO.updateBorrowDuration(selectedBorrowedBook.getId(), selectedBorrowedBook.getBorrowDuration() + durationCbox_extendForm.getValue());
			}
		}else if(event.getSource() == cancelButton_extendForm) {
			extensionForm.setVisible(false);
			emptyBorrowedBookCard();
		}else if(event.getSource() == doneButton_extendForm) {
			extensionForm.setVisible(false);
			details_extendForm.setVisible(false);
			emptyBorrowedBookCard();
		}
	}
	
	public void openExtendForm(BookDTO book, BorrowedBookDTO selectedBorrowedBook) {
		this.selectedBorrowedBook = selectedBorrowedBook;
		extensionForm.setVisible(true);
		message_extendForm.setVisible(false);
		emptyBorrowedBookCard();
		
		HBox cardBox = null;
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/fxml/borrowedBooks_card.fxml"));
		try {
			cardBox = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BorrowedBookCardController bookController = fxmlLoader.getController();
		bookController.setData(book, selectedBorrowedBook, this);
		bookCard_extendForm.getChildren().add(cardBox);
	}
	
	private void emptyBorrowedBookCard(){
		if(!bookCard_extendForm.getChildren().isEmpty()) {
			bookCard_extendForm.getChildren().remove(0);
			message_extendForm.setVisible(true);
		}
	}
	

}
