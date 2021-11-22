package application;

import database.DB_Insert;
import database.DB_Update;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import static application.Main.frame;

public class NewBookWindow extends JDialog {
    private JPanel contentPane;
    private JPanel Header;
    private JLabel Logo;
    private JPanel RightBody;
    private JButton OK;
    private JButton Cancel;
    private JTextField TitleField;
    private JTextField AuthorField;
    private JTextField YearField;
    private JTextField Isbn;

    private MainMenu mainMenu;
    private int editBookID;

    NewBookWindow(MainMenu mainMenu, int editBookID) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(OK);

        this.mainMenu = mainMenu;
        this.editBookID = editBookID;

        //set logo's image
        ImageIcon newImg = new ImageIcon("src/resources/images/Logo.png");
        Logo.setBounds(0, 0, 133, 100);
        newImg.setImage(newImg.getImage().getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH));
        Logo.setIcon(newImg);


        //set OK button's image
        newImg = new ImageIcon("src/resources/images/btn_G.png");
        OK.setBounds(0, 0, 150, 60);
        newImg.setImage(newImg.getImage().getScaledInstance(OK.getWidth(), OK.getHeight(), Image.SCALE_SMOOTH));
        OK.setIcon(newImg);

        //set Cancel button's image
        newImg = new ImageIcon("src/resources/images/btn_R.png");
        Cancel.setBounds(0, 0, 150, 60);
        newImg.setImage(newImg.getImage().getScaledInstance(Cancel.getWidth(), Cancel.getHeight(), Image.SCALE_SMOOTH));
        Cancel.setIcon(newImg);

        OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnOK();
            }
        });

        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnCancel();
            }
        });

    }

    void UpdateFields(String oldTitle, String oldAuthor, String oldYear) {
        TitleField.setText(oldTitle);
        AuthorField.setText(oldAuthor);
        YearField.setText(oldYear);
    }

    private void OnOK() {
        //If editBookID is -1 - add a new book
        if (editBookID == -1) {
            //If any field is empty - show alert window, else - add a book
            if (TitleField.getText().isEmpty() || AuthorField.getText().isEmpty() || YearField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Some fields might be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!CheckYear()) {
                JOptionPane.showMessageDialog(frame, "Year of publishing is wrong", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DB_Insert.InsertBook(TitleField.getText(), AuthorField.getText(), YearField.getText());
                dispose();
                mainMenu.UpdateTableOfBooks();
                JOptionPane.showMessageDialog(frame, "A new book has \nbeen added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            //If any field is empty - show alert window, else - update a book info
            if (TitleField.getText().isEmpty() || AuthorField.getText().isEmpty() || YearField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Some fields might be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!CheckYear()) {
                JOptionPane.showMessageDialog(frame, "Year of publishing is wrong", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DB_Update.EditBook(editBookID, TitleField.getText(), AuthorField.getText(), YearField.getText());
                dispose();
                mainMenu.UpdateTableOfBooks();
                JOptionPane.showMessageDialog(frame, "The book has been \nupdated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    private void OnCancel() {
        dispose();
    }

    //Method to check if entered date is ok
    private boolean CheckYear() {
        //If length is wrong - return false
        if (YearField.getText().length() != 4)
            return false;

        //If date is incorrect - return false
        int year = Integer.parseInt(YearField.getText());
        String currentDate = LocalDate.now().toString();
        int cYear = Integer.parseInt(currentDate.substring(0, 4));
        if (year < 1000 || year > cYear)
            return false;
        //If everything is OK - return true;
        return true;
    }

}
