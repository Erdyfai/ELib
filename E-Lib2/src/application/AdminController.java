package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.BookDAO;
import dao.UserDAO;
import dto.BookDTO;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AdminController implements Initializable{

    @FXML
    private Button studentBtn_form;

    @FXML
    private VBox approveBook_form;

    @FXML
    private VBox returnValidate_form;

    @FXML
    private Button approveBtn_menu;

    @FXML
    private Button approve_btn;

    @FXML
    private Button bookBtn_form;

    @FXML
    private TableView<BookDTO> bookListTable;

    @FXML
    private TableColumn<BookDTO, String> category_column;

    @FXML
    private AnchorPane crudBooks_form;

    @FXML
    private Button homeBtn_form;

    @FXML
    private Button imposeSanctionBtn_menu;

    @FXML
    private Button imposeSanction_Btn;

    @FXML
    private VBox imposeSanction_form;

    @FXML
    private TextField inputViolation;

    @FXML
    private Button returnBtn_menu;

    @FXML
    private TextField searchBook;

    @FXML
    private ComboBox<?> selectOrderBook;

    @FXML
    private ComboBox<?> selectReturnBook;

    @FXML
    private ComboBox<?> selectViolation;

    @FXML
    private TableColumn<BookDTO, Integer> stock_column;

    @FXML
    private TableColumn<UserDTO, Integer> studentBookOrder_column;

    @FXML
    private TableColumn<UserDTO, Integer> studentBorrowBooks_column;

    @FXML
    private VBox studentDetails_stdForm;

    @FXML
    private TableColumn<UserDTO, String> studentEmail_column;

    @FXML
    private TableColumn<UserDTO, String> studentFaculty_column;

    @FXML
    private Label studentFaculty_details;

    @FXML
    private VBox studentMenu;

    @FXML
    private TableColumn<UserDTO, String> studentName_column;

    @FXML
    private TableColumn<UserDTO, String> studentNim_column;

    @FXML
    private Label studentNim_details;

    @FXML
    private Label studentProgram_details;

    @FXML
    private TableColumn<UserDTO, String> studentSanction_column;

    @FXML
    private TableColumn<UserDTO, String> studentStudyProgram_nim;

    @FXML
    private TableView<UserDTO> studentTable;

    @FXML
    private AnchorPane student_form;

    @FXML
    private Label studetnName_detais;

    @FXML
    private TableColumn<BookDTO, String> title_column;

    @FXML
    private Button validate_btn;

    @FXML
    private TableColumn<BookDTO, String> writer_column;
    
    @FXML
    private AnchorPane homeForm;
    
    @FXML
    private Button cancelBtn_iSform;
    
    @FXML
    private Button cancelBtn_approveForm;
    
    @FXML
    private Button cancelBtn_validateForm;

    private ObservableList<UserDTO> studentList = FXCollections.observableArrayList();
    private ObservableList<BookDTO> bookList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

            UserDAO userDAO = new UserDAO();
            BookDAO bookDAO = new BookDAO();

            List<UserDTO> students = userDAO.getAllStudent();
            studentList.addAll(students);

            List<BookDTO> books = bookDAO.selectAllBooks();
            bookList.addAll(books);

            studentName_column.setCellValueFactory(new PropertyValueFactory<>("name"));
            studentEmail_column.setCellValueFactory(new PropertyValueFactory<>("email"));
            studentNim_column.setCellValueFactory(new PropertyValueFactory<>("nim"));
            studentFaculty_column.setCellValueFactory(new PropertyValueFactory<>("faculty"));
            studentStudyProgram_nim.setCellValueFactory(new PropertyValueFactory<>("studyProgram"));
            studentBorrowBooks_column.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));
            studentBookOrder_column.setCellValueFactory(new PropertyValueFactory<>("countBookOrder"));
            studentSanction_column.setCellValueFactory(new PropertyValueFactory<>("sanctions"));

            title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
            writer_column.setCellValueFactory(new PropertyValueFactory<>("writer"));
            category_column.setCellValueFactory(new PropertyValueFactory<>("category"));
            stock_column.setCellValueFactory(new PropertyValueFactory<>("stock"));

            studentTable.setItems(studentList);
            bookListTable.setItems(bookList);

        
    }
    
    @FXML
    public void switchStudentForm(ActionEvent event) {
    	if (event.getSource()== imposeSanctionBtn_menu) {
			imposeSanction_form.setVisible(true);
			approveBook_form.setVisible(false);
			returnValidate_form.setVisible(false);
		} else if(event.getSource()== approveBtn_menu) {
			imposeSanction_form.setVisible(false);
			approveBook_form.setVisible(true);
			returnValidate_form.setVisible(false);
		}else if(event.getSource()== returnBtn_menu) {
			imposeSanction_form.setVisible(false);
			approveBook_form.setVisible(false);
			returnValidate_form.setVisible(true);
		}else if(event.getSource()== cancelBtn_iSform) {
			imposeSanction_form.setVisible(false);
		}else if(event.getSource()== cancelBtn_approveForm) {
			approveBook_form.setVisible(false);
		}else if(event.getSource()== cancelBtn_validateForm) {
			returnValidate_form.setVisible(false);
		}
    }
	
	@FXML
	public void switchMainForm(ActionEvent event) {
		if (event.getSource()== homeBtn_form) {
			student_form.setVisible(false);
			crudBooks_form.setVisible(false);
		} else if(event.getSource()== studentBtn_form) {
			student_form.setVisible(true);
			crudBooks_form.setVisible(false);
		}else if(event.getSource()== bookBtn_form) {
			student_form.setVisible(false);
			crudBooks_form.setVisible(true);
		}
	}

}
