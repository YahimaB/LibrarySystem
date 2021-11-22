package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DB_Update {

    //Method to update borrowing status
    public static void UpdateBorrow(int status, String borrowDate, int bookID) {
        String sql = "UPDATE Borrowing SET Status = ? WHERE ReaderID = ? AND BorrowDate = ? AND BookID = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, status);
            pstmt.setString(2, borrowDate);
            pstmt.setInt(3, bookID);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

    //Method to edit book's information in database
    public static void EditBook(int bookID,String isbn, String newTitle, String newAuthor, String newYear) {
        String sql = "UPDATE Books SET ISBN = ?, Title = ?, Author = ?, YearOfPublishing = ? WHERE BookID = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.setString(2, newTitle);
            pstmt.setString(3, newAuthor);
            pstmt.setString(4, newYear);
            pstmt.setInt(5, bookID);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }
}
