package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/LibUmm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_USER_SQL = "INSERT INTO users" +
        "  (name, email, password, telephone, nim, faculty, study_program, borrowed_books, sanctions, user_type, image_path) VALUES " +
        " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id, name, email, password, telephone, nim, faculty, study_program, borrowed_books, sanctions, user_type, image_path from users where id =?";
    
    private static final String SELECT_USER_BY_EMAIL = "select id, name, email, password, telephone, nim, faculty, study_program, borrowed_books, sanctions, user_type, image_path from users where email =?";
    
    private static final String SELECT_USER_BY_TYPE = "SELECT * FROM users WHERE user_type = ?";
    
    // Method to get a database connection
    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to insert a user
    public void insertUser(UserDTO user) throws SQLException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getTelephone());
            preparedStatement.setString(5, user.getNim());
            preparedStatement.setString(6, user.getFaculty());
            preparedStatement.setString(7, user.getStudyProgram());
            preparedStatement.setInt(8, user.getBorrowedBooks());
            preparedStatement.setString(9, user.getSanctions());
            preparedStatement.setString(10, user.getUserType());
            preparedStatement.setString(11, user.getImagePath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public List<UserDTO> getAllStudent() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_TYPE);) {
            preparedStatement.setString(1, "student");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telephone = rs.getString("telephone");
                String nim = rs.getString("nim");
                String faculty = rs.getString("faculty");
                String studyProgram = rs.getString("study_program");
                int borrowedBooks = rs.getInt("borrowed_books");
                String sanctions = rs.getString("sanctions");
                String imagePath = rs.getString("image_path");
                String dbUserType = rs.getString("user_type");
                
                // Filter only users of type "student"
                if ("student".equalsIgnoreCase(dbUserType)) {
                    UserDTO user = new UserDTO();
                    user.setId(id);
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setTelephone(telephone);
                    user.setNim(nim);
                    user.setFaculty(faculty);
                    user.setStudyProgram(studyProgram);
                    user.setBorrowedBooks(borrowedBooks);
                    user.setSanctions(sanctions);
                    user.setUserType(dbUserType);
                    user.setImagePath(imagePath);
                    user.setCountBookOrder(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }
    // Method to select a user by ID
    public UserDTO selectUserById(int id) {
        UserDTO user = null;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telephone = rs.getString("telephone");
                String nim = rs.getString("nim");
                String faculty = rs.getString("faculty");
                String studyProgram = rs.getString("study_program");
                int borrowedBooks = rs.getInt("borrowed_books");
                String sanctions = rs.getString("sanctions");
                String userType = rs.getString("user_type");
                String imagePath = rs.getString("image_path");
                user = new UserDTO();
                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setTelephone(telephone);
                user.setNim(nim);
                user.setFaculty(faculty);
                user.setStudyProgram(studyProgram);
                user.setBorrowedBooks(borrowedBooks);
                user.setSanctions(sanctions);
                user.setUserType(userType);
                user.setImagePath(imagePath);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

	public UserDTO selectUserByEmail(String email) {
		  UserDTO user = null;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);) {
	            preparedStatement.setString(1, email);
	            ResultSet rs = preparedStatement.executeQuery();
	            while (rs.next()) {
	            	int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String password = rs.getString("password");
	                String telephone = rs.getString("telephone");
	                String nim = rs.getString("nim");
	                String faculty = rs.getString("faculty");
	                String studyProgram = rs.getString("study_program");
	                int borrowedBooks = rs.getInt("borrowed_books");
	                String sanctions = rs.getString("sanctions");
	                String userType = rs.getString("user_type");
	                String imagePath = rs.getString("image_path");
	                user = new UserDTO();
	                user.setId(id);
	                user.setName(name);
	                user.setEmail(email);
	                user.setPassword(password);
	                user.setTelephone(telephone);
	                user.setNim(nim);
	                user.setFaculty(faculty);
	                user.setStudyProgram(studyProgram);
	                user.setBorrowedBooks(borrowedBooks);
	                user.setSanctions(sanctions);
	                user.setUserType(userType);
	                user.setImagePath(imagePath);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return user;
	}
	  private void printSQLException(SQLException ex) {
	        for (Throwable e : ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
}
