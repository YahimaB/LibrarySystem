package application;

import database.DB_Insert;
import database.DB_Select;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static application.Main.frame;


public class Borrowing extends JDialog {
    private JPanel contentPane;
    private JPanel Header;
    private JLabel Logo;
    private JPanel RightBody;
    private JButton OK;
    private JButton Cancel;
    private JTextField LastField;
//    private JTextField FirstField;
    private JTextField BookField;
    private JTextPane TextPane;
    private JTextField BorrowField;
    private JTextField ReturnField;
    private JLabel readerName;

    public Borrowing() {
        setContentPane(contentPane);
        setModal(true);

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
                OnOk();
            }
        });

        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnCancel();
            }
        });

        LastField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                UpdateReaderName();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                UpdateReaderName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                UpdateReaderName();
            }
        });
    }

    private void UpdateReaderName() {
        String name = DB_Select.SelectReaderName(LastField.getText());
        if (name.isEmpty()) name = "No reader";
        readerName.setText(name);
    }

    private void OnOk() {
        boolean isEverythingOk = true;
        TextPane.setText("");

        //Check if all field are filled, else show error message
        if (LastField.getText().isEmpty()) {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "Reader's id is empty\n");
        }

        if (readerName.getText().isEmpty() || readerName.getText() == "No reader") {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "No reader found\n");
        }

        if (BookField.getText().isEmpty()) {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "Book's ID is empty\n");
        }
        else if (DB_Select.WasBookBorrowed(BookField.getText()))
        {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "This book has already been borrowed\n");
        }

        if (BorrowField.getText().isEmpty()) {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "Borrow date is empty\n");
        }

        if (ReturnField.getText().isEmpty()) {
            isEverythingOk = false;
            TextPane.setText(TextPane.getText() + "Return date is empty\n");
        }

        if (!CheckDate(BorrowField.getText()) || !CheckDate(ReturnField.getText())) {
            TextPane.setText(TextPane.getText() + "Borrow and/or Return date is wrong\n");
            return;
        }

        String[] name = readerName.getText().split(" ");
        //If fields are filled - add a new borrowing to database
        if (isEverythingOk) {
            String readerID = DB_Select.SelectReaderID(name[0], name[1]);
            if (readerID.equals("")) {
                JOptionPane.showMessageDialog(frame, "There is no person with \nthis credentials", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DB_Insert.InsertBorrow(readerID, BookField.getText(), Main.CurrentLibrarianID, BorrowField.getText(), ReturnField.getText());
            dispose();
            JOptionPane.showMessageDialog(frame, "A new borrowing has \nbeen created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void OnCancel() {
        dispose();
    }

    //Method to check if dates are correct
    private boolean CheckDate(String date) {
        if (date.length()<10)
            return false;

        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));

        if (day <= 0 || day > 31)
            return false;
        if (month <=0 || month > 12)
            return false;
        if (year <= 1000 || year > 3000)
            return false;

        return true;
    }
}
