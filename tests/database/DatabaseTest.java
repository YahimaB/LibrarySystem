package database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    @BeforeAll
    static void Setup() {
        DB_Connect.url = "jdbc:sqlite:tests/resources/DataBase.db";
    }

    @Test
    void bookSelect() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("0", "1111111111111", "testTitle", "testAuthor", "2000"));
        assertBooks(books);
    }

    @Test
    void bookInsertAndDelete() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("0", "1111111111111", "testTitle", "testAuthor", "2000"));

        Book insertBook = new Book("1", "1111111111112", "testTitle2", "testAuthor2", "2001");
        books.add(insertBook);

        DB_Insert.InsertBook(insertBook.isbn, insertBook.title, insertBook.author, insertBook.year);
        assertBooks(books);

        DB_Delete.DeleteBook("1");
        books.remove(insertBook);
        assertBooks(books);
    }

    @Test
    void borrowAndReturnBook() {
        ArrayList<Borrow> borrows = new ArrayList<>();
        assertBorrows(borrows);

        Borrow insertBorrow = new Borrow("1", "0", 0, "17.12.2021", "28.12.2021", 0);
        borrows.add(insertBorrow);
        DB_Insert.InsertBorrow(insertBorrow.ReaderID, insertBorrow.BookID, insertBorrow.LibrarianID, insertBorrow.BorrowDate, insertBorrow.ReturnDate);

        assertBorrows(borrows);
    }

    private void assertBooks(ArrayList<Book> books) {
        ArrayList<ArrayList> booksList = new ArrayList<>();
        DB_Select.SelectAllBooks("", booksList);

        assertEquals(books.toString(), booksList.toString());
    }

    private void assertBorrows(ArrayList<Borrow> borrows) {
        ArrayList<ArrayList> borrowsList = new ArrayList<>();
        DB_Select.SelectAllBorrows("", borrowsList);

        assertEquals(borrows.toString(), borrowsList.toString());
    }

    private class Book {
        private String id;
        private String isbn;
        private String title;
        private String author;
        private String year;

        public Book(String id, String isbn, String title, String author, String year) {
            this.id = id;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.year = year;
        }

        public String toString() {
            return "[" + id + ", " + isbn + ", " + title + ", " + author + ", " + year + "]";
        }
    }

    private class Borrow {
        private String ReaderID;
        private String BookID;
        private int LibrarianID;
        private String BorrowDate;
        private String ReturnDate;
        private int Status;

        public Borrow(String ReaderID, String BookID, int LibrarianID, String BorrowDate, String ReturnDate, int Status) {
            this.ReaderID = ReaderID;
            this.BookID = BookID;
            this.LibrarianID = LibrarianID;
            this.BorrowDate = BorrowDate;
            this.ReturnDate = ReturnDate;
            this.Status = Status;
        }

        public String toString() {
            return "[" + ReaderID + ", " + BookID + ", " + LibrarianID + ", " + BorrowDate + "]";
        }
    }
}
