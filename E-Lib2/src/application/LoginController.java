package application;

import java.io.IOException;

import dao.UserDAO;
import dto.SessionManager;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginController {
	

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;

    @FXML
    private Text message;

    @FXML
    private PasswordField passwordField;

	
	private UserDAO userDAO = new UserDAO();
	
	public void login(ActionEvent event) throws IOException{
		String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter your email and password.");
            return;
        }

        UserDTO user = userDAO.selectUserByEmail(email);
        
        // hash pw ke database dlu pai goblok baru pake bcrypt
        if (user != null && password.equals(user.getPassword())) {
        	SessionManager.login(user);
            if ("admin".equalsIgnoreCase(user.getUserType())) {
                loadAdminDashboard();
            } else if ("student".equalsIgnoreCase(user.getUserType())) {
                loadStudentDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Error!", "Unknown user type.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error!", "Invalid email or password.");
        }
    }

    private void loadAdminDashboard() {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentDashboard() {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Student.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
}
