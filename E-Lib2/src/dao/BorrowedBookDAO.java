package dao;

import dto.BorrowedBookDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/LibUmm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String SELECT_ALL_BORROWED_BOOKS = "SELECT * FROM borrowed_books";
    
    private static final String INSERT_BORROWED_BOOK = "INSERT INTO borrowed_books (student_id, book_id, borrow_duration, borrow_date, return_date, borrow_status) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_BORROW_DURATION = "UPDATE borrowed_books SET borrow_duration = ? WHERE id = ?";
    
    private static final String COUNT_ORDER_BOOK = "SELECT COUNT(*) FROM borrowed_books WHERE student_id = ? AND borrow_status = ?";
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public int countBorrowStatus(int studentId, String status) {
        
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(COUNT_ORDER_BOOK)) {
            
            pstmt.setInt(1, studentId);
            pstmt.setString(2, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
        	printSQLException(e);
        }
        return 0;
    }
    
    public void updateBorrowDuration(int borrowedBookId, int newDuration) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BORROW_DURATION)) {
            
            statement.setInt(1, newDuration);
            statement.setInt(2, borrowedBookId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Borrowed book with ID " + borrowedBookId + " updated successfully.");
            } else {
                System.out.println("No borrowed book found with ID " + borrowedBookId);
            }

        } catch (SQLException e) {
        	printSQLException(e);
        }
    }
    
    public void addBorrowedBook(int studentId, int bookId, int borrowDuration, Date borrowDate, Date returnDate, String status) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BORROWED_BOOK)) {

            statement.setInt(1, studentId);
            statement.setInt(2, bookId);
            statement.setInt(3, borrowDuration);
            statement.setDate(4, borrowDate);
            statement.setDate(5, returnDate);
            statement.setString(6, status);

            statement.executeUpdate();
        } catch (SQLException e) {
        	printSQLException(e);
        }
    }

    // Method to get all borrowed books
    public List<BorrowedBookDTO> selectAllBorrowedBooks() {
        List<BorrowedBookDTO> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BORROWED_BOOKS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("student_id");
                int bookId = rs.getInt("book_id");
                java.sql.Date borrowDate = rs.getDate("borrow_date");
                java.sql.Date returnDate = rs.getDate("return_date");
                int borrowDuration = rs.getInt("borrow_duration");

                BorrowedBookDTO borrowedBook = new BorrowedBookDTO();
                borrowedBook.setId(id);
                borrowedBook.setStudentId(studentId);
                borrowedBook.setBookId(bookId);
                borrowedBook.setBorrowDate(new java.util.Date(borrowDate.getTime()));
                if (returnDate != null) {
                    borrowedBook.setReturnDate(new java.util.Date(returnDate.getTime()));
                }
                borrowedBook.setBorrowDuration(borrowDuration);

                borrowedBooks.add(borrowedBook);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
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
