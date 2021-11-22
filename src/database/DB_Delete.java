package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static application.Main.frame;

public class DB_Delete {

    //Method to delete a book from database
    public static void DeleteBook(String param) {

        String sql = "DELETE FROM Books WHERE BookID = ?";

        if (DB_Select.WasBookBorrowed(param))
        {
            JOptionPane.showMessageDialog(frame, "Book cannot be deleted.\n It is borrowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, param);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }

    }

    //Method to delete a borrowing from database
    public static void ReturnBook(String param) {

        String sql = "DELETE FROM Borrowings WHERE BorrowID = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, param);
            pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }

    }

}
