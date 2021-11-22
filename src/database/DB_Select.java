package database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class DB_Select {

    //Method to select reader's id from database by first and last name
    public static String SelectReaderID(String firstName, String lastName) {
        String answer = "";
        String sql = "SELECT Readers.ReaderID FROM Readers WHERE FirstName = ? AND LastName = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet queryResult = pstmt.executeQuery();
            //If anything was found
            if (queryResult.next()) {
                answer = answer + queryResult.getString(1);
            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }

        return answer;
    }

    //Method to get librarian's hashed password from database
    public static String GetLibrarianPassword(String firstName, String lastName) {
        String answer = "";
        String sql = "SELECT Librarians.Password FROM Librarians WHERE FirstName = ? AND LastName = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet queryResult = pstmt.executeQuery();
            //If anything was found
            if (queryResult.next()) {
                answer = answer + queryResult.getString(1);
            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }

        return answer;
    }

    //Method to detect if this book was not borrowed
    public static boolean WasBookBorrowed(String bookID) {
        boolean answer = false;
        String sql = "SELECT Borrowings.BorrowID FROM Borrowings WHERE BookID = ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bookID);
            ResultSet queryResult = pstmt.executeQuery();

            //If anything was found
            if (queryResult.next()) {
                answer = true;
            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }

        return answer;
    }

    //Method to get the table of all books
    public static void SelectAllBooks(String BookName, ArrayList<ArrayList> OutList) {
        String sql = "SELECT * FROM Books WHERE Title LIKE ?";
        SelectAllRows(sql, 4, BookName, OutList);
    }

    //Method to get the table of all readers
    public static void SelectAllReaders(String ReaderName, ArrayList<ArrayList> OutList) {
        String sql = "SELECT * FROM Readers WHERE FirstName LIKE ?";
        SelectAllRows(sql, 5, ReaderName, OutList);
    }

    //Select all rows from a specific table
    static void SelectAllRows(String sql, int columnCount, String param, ArrayList<ArrayList> OutList) {

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,"%"+param+"%");
            ResultSet queryResult = pstmt.executeQuery();

            //while there are more rows to read
            while (queryResult.next()) {
                ArrayList<String> newRow = new ArrayList<>();
                String newCell;
                for (int i = 1; i <= columnCount; i++) {
                    newCell = queryResult.getString(i);
                    newRow.add(newCell);
                }
                OutList.add(newRow);
            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

    //Method to get the table of all borrowings
    public static void SelectAllBorrows(String name, ArrayList<ArrayList> OutList) {
        String sql = "SELECT Borrowings.BorrowID, Borrowings.BorrowDate, Readers.FirstName, Books.Title FROM Borrowings " +
                "INNER JOIN Readers ON Borrowings.ReaderID = Readers.ReaderID " +
                "INNER JOIN Books ON Borrowings.BookID = Books.BookID " +
                "WHERE Readers.FirstName LIKE ?";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,"%"+name+"%");
            ResultSet queryResult = pstmt.executeQuery();

            while (queryResult.next()) {
                ArrayList<String> newRow = new ArrayList<>();
                String newCell;
                for (int i = 1; i <= 4; i++) {
                    newCell = queryResult.getString(i);
                    newRow.add(newCell);
                }
                OutList.add(newRow);
            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

    //Method to get the table of all debtores
    public static void SelectAllDebtores(ArrayList<ArrayList> OutList) {
        String sql = "SELECT Readers.FirstName, Readers.LastName, Borrowings.ReturnDate FROM Borrowings " +
                "INNER JOIN Readers ON Borrowings.ReaderID = Readers.ReaderID " +
                "INNER JOIN Books ON Borrowings.BookID = Books.BookID " +
                "WHERE Borrowings.Status = 0 " +
                "ORDER BY Readers.FirstName";

        try {
            Connection connection = DB_Connect.Connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet queryResult = pstmt.executeQuery();

            while (queryResult.next()) {
                //Get today's date
                String currentDate = LocalDate.now().toString();
                String returnDate = queryResult.getString(3);

                //If it is late to return the book, add new row
                if(IsLateDate(returnDate, currentDate)) {
                    ArrayList<String> newRow = new ArrayList<>();
                    String newCell;
                    for (int i = 1; i <= 2; i++) {
                        newCell = queryResult.getString(i);
                        newRow.add(newCell);
                    }
                    OutList.add(newRow);
                }

            }
            connection.close();

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println(e.getMessage());
        }
    }

    //Method to check if the date is earlier than TODAY
    private static boolean IsLateDate(String rDate, String cDate){
        int rDay = Integer.parseInt(rDate.substring(0, 2));
        int rMonth = Integer.parseInt(rDate.substring(3, 5));
        int rYear = Integer.parseInt(rDate.substring(6, 10));

        int cDay = Integer.parseInt(cDate.substring(8, 10));
        int cMonth = Integer.parseInt(cDate.substring(5, 7));
        int cYear = Integer.parseInt(cDate.substring(0, 4));


        if (rYear < cYear) {
            return true;
        } else if (rYear == cYear) {
            if (rMonth < cMonth) {
                return true;
            } else if (rMonth == cMonth) {
                if (rDay < cDay) {
                    return true;
                }
            }
        }

        return false;
    }
}
