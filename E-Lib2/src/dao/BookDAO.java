package dao;

import dto.BookDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/LibUmm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    
    private static final String SELECT_BOOK_BY_ID = "select book_id, title, writer, category, stock, image_path from books where book_id =?";

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

    // Method to get all books
    public List<BookDTO> selectAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");
                String imagePath = rs.getString("image_path");

                BookDTO book = new BookDTO();
                book.setBookId(bookId);
                book.setTitle(title);
                book.setWriter(writer);
                book.setCategory(category);
                book.setStock(stock);
                book.setImagePath(imagePath);

                books.add(book);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return books;
    }
    
    public BookDTO selectBookById(int bookId) {
        BookDTO book = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);) {
            preparedStatement.setInt(1, bookId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");
                String imagePath = rs.getString("image_path");
                book = new BookDTO();
                book.setBookId(bookId);
                book.setTitle(title);
                book.setWriter(writer);
                book.setCategory(category);
                book.setStock(stock);
                book.setImagePath(imagePath);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return book;
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
