package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DB_Insert {

    //Method to add a new book to database
    public static void InsertBook(String isbn, String bookTitle, String bookAuthor, String bookYear) {
        String sql = "INSERT INTO Books(ISBN, Title, Author, YearOfPublishing) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.setString(2, bookTitle);
            pstmt.setString(3, bookAuthor);
            pstmt.setString(4, bookYear);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

    //Method to add a new borrowing to database
    public static void InsertBorrow(String readerID, String bookID, int librarianID, String borrowDate, String returnDate) {
        String sql = "INSERT INTO Borrowings(ReaderID, BookID, LibrarianID, BorrowDate, ReturnDate,  Status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, readerID);
            pstmt.setString(2, bookID);
            pstmt.setInt(3, librarianID);
            pstmt.setString(4, borrowDate);
            pstmt.setString(5, returnDate);
            pstmt.setInt(6, 0);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

}
