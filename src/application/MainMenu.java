package application;

import database.DB_Delete;
import database.DB_Insert;
import database.DB_Select;
import database.DB_Update;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static application.Main.frame;


public class MainMenu extends JDialog {
    private JPanel contentPane;
    private JScrollPane MainScrollPanel;
    private JTable MainTable;
    private JTextArea nameInput;
    private JPanel Header;
    private JPanel MainBody;
    private JPanel Footer;
    private JPanel RightBody;
    private JLabel Logo;
    private JButton EditBook; //
    private JButton RemoveBook; //
    private JButton AddBook; //
    private JButton AdminRequest;
    private JButton AllBooks;
    private JButton AllBorrows;
    private JButton Borrow;
    private JButton Return;
    private JScrollPane DebtorScrollPanel;
    private JTable DebtorTable;

    private boolean isShowingBooks;

    public MainMenu() {
        setContentPane(contentPane);
        setModal(true);
        ShowTableOfBooks("");
        isShowingBooks = true;
        ShowTableOfDebtors();

        //set logo's image
        ImageIcon newImg = new ImageIcon("src/resources/images/Logo.png");
        Logo.setBounds(0, 0, 200, 150);
        newImg.setImage(newImg.getImage().getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH));
        Logo.setIcon(newImg);

        //set AddBook button's image
        newImg = new ImageIcon("src/resources/images/btn_Y.png");
        AddBook.setBounds(0, 0, 280, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(AddBook.getWidth(), AddBook.getHeight(), Image.SCALE_SMOOTH));
        AddBook.setIcon(newImg);

        //set RemoveBook button's image
        newImg = new ImageIcon("src/resources/images/btn_Y.png");
        RemoveBook.setBounds(0, 0, 260, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(RemoveBook.getWidth(), RemoveBook.getHeight(), Image.SCALE_SMOOTH));
        RemoveBook.setIcon(newImg);

        //set EditBook button's image
        newImg = new ImageIcon("src/resources/images/btn_Y.png");
        EditBook.setBounds(0, 0, 220, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(EditBook.getWidth(), EditBook.getHeight(), Image.SCALE_SMOOTH));
        EditBook.setIcon(newImg);

        //set AdminRequest button's image
        newImg = new ImageIcon("src/resources/images/btn_B.png");
        AdminRequest.setBounds(0, 0, 300, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(AdminRequest.getWidth(), AdminRequest.getHeight(), Image.SCALE_SMOOTH));
        AdminRequest.setIcon(newImg);

        //set Borrow button's image
        newImg = new ImageIcon("src/resources/images/btn_R.png");
        Borrow.setBounds(0, 0, 180, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(Borrow.getWidth(), Borrow.getHeight(), Image.SCALE_SMOOTH));
        Borrow.setIcon(newImg);

        //set Return button's image
        newImg = new ImageIcon("src/resources/images/btn_R.png");
        Return.setBounds(0, 0, 180, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(Return.getWidth(), Return.getHeight(), Image.SCALE_SMOOTH));
        Return.setIcon(newImg);

        //set Books button's image
        newImg = new ImageIcon("src/resources/images/btn_G.png");
        AllBooks.setBounds(0, 0, 160, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(AllBooks.getWidth(), AllBooks.getHeight(), Image.SCALE_SMOOTH));
        AllBooks.setIcon(newImg);

        //set Borrows button's image
        newImg = new ImageIcon("src/resources/images/btn_G.png");
        AllBorrows.setBounds(0, 0, 160, 42);
        newImg.setImage(newImg.getImage().getScaledInstance(AllBorrows.getWidth(), AllBorrows.getHeight(), Image.SCALE_SMOOTH));
        AllBorrows.setIcon(newImg);

        AddBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnAddBook();
            }
        });

        EditBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnEditBook();
            }
        });

        RemoveBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnRemoveBook();
            }
        });

        Borrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnBorrow();
            }
        });

        Return.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnReturn();
            }
        });

        AllBooks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnAllBooks();
                isShowingBooks = true;
            }
        });

        AllBorrows.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnAllReaders();
                isShowingBooks = false;
            }
        });

        AdminRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnAdminRequest();
            }
        });

        nameInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (isShowingBooks)
                    ShowTableOfBooks(nameInput.getText());
                else
                    ShowTableOfBorrows(nameInput.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (isShowingBooks)
                    ShowTableOfBooks(nameInput.getText());
                else
                    ShowTableOfBorrows(nameInput.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (isShowingBooks)
                    ShowTableOfBooks(nameInput.getText());
                else
                    ShowTableOfBorrows(nameInput.getText());
            }
        });

        MainTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (isShowingBooks && mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    OnEditBook();
                }
            }
        });
    }
    
    void OnAddBook() {
        //Open a NewBookWindow
        NewBookWindow frame = new NewBookWindow(this, -1);

        frame.setSize(new Dimension(500, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setVisible(true);

    }

    void OnEditBook() {

        //Get selected row and open NewBookWindow in editing mode
        int selectedRow = MainTable.getSelectedRow();
        if (isShowingBooks && selectedRow != -1) {
            String bookID = String.valueOf(MainTable.getValueAt(selectedRow, 0));
            NewBookWindow frame = new NewBookWindow(this, Integer.parseInt(bookID));

            String oldTitle = String.valueOf(MainTable.getValueAt(selectedRow, 1));
            String oldAuthor = String.valueOf(MainTable.getValueAt(selectedRow, 2));
            String oldYear = String.valueOf(MainTable.getValueAt(selectedRow, 3));

            frame.UpdateFields(oldTitle, oldAuthor, oldYear);

            frame.setSize(new Dimension(500, 500));
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "No book has been selected", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void OnRemoveBook() {
        //Get selected row and delete it from database
        int selectedRow = MainTable.getSelectedRow();
        if (isShowingBooks && selectedRow != -1) {
            String bookID = String.valueOf(MainTable.getValueAt(selectedRow, 0));
            DB_Delete.DeleteBook(bookID);
            ShowTableOfBooks("");
            ShowTableOfDebtors();
        } else {
            JOptionPane.showMessageDialog(frame, "No book has been selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void OnBorrow() {
        //Open a Borrowing window
        Borrowing frame = new Borrowing();

        frame.setSize(new Dimension(600, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setVisible(true);
    }

    void OnReturn() {
        //Get selected row and delete it from database
        int selectedRow = MainTable.getSelectedRow();
        if (!isShowingBooks && selectedRow != -1) {
            String borrowID = String.valueOf(MainTable.getValueAt(selectedRow, 0));
            DB_Delete.ReturnBook(borrowID);
            ShowTableOfBooks("");
            ShowTableOfDebtors();
        } else {
            JOptionPane.showMessageDialog(frame, "No borrowing has been selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Show table of all books without search criteria
    private void OnAllBooks() {
        ShowTableOfBooks("");
    }

    //Show table of all borrowings without search criteria
    private void OnAllReaders() {
        ShowTableOfBorrows("");
    }

    private void OnAdminRequest() {
        //Open AdminRequest window
        AdminRequest frame = new AdminRequest();

        frame.setSize(new Dimension(500, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setVisible(true);
    }

    //Method to call from other windows to update the table of books
    void UpdateTableOfBooks() {
        ShowTableOfBooks("");
    }

    //Method to update the table of books
    private void ShowTableOfBooks(String bookTitle) {
        MainTable.setAutoCreateRowSorter(true);
        MainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        MainScrollPanel.getHorizontalScrollBar().setUnitIncrement(4);
        MainScrollPanel.getViewport().setBackground(Color.decode("#616183"));

        ArrayList<ArrayList> booksList = new ArrayList<>();
        String[] columnNames = {"Book ID", "Title", "Author", "Year of publishing"};

        DB_Select.SelectAllBooks(bookTitle, booksList);

        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 4; }
            public int getRowCount() { return booksList.size(); }
            public Object getValueAt(int row, int col) { return booksList.get(row).get(col); }
            public String getColumnName(int index) { return columnNames[index]; }
        };

        MainTable.setModel(dataModel);

        MainTable.getColumnModel().getColumn(0).setMinWidth(60);
        MainTable.getColumnModel().getColumn(0).setMaxWidth(60);

        MainTable.getColumnModel().getColumn(3).setMinWidth(110);
        MainTable.getColumnModel().getColumn(3).setMaxWidth(110);

    }

    //Method to update the table of borrowings
    private void ShowTableOfBorrows(String readerName) {
        MainTable.setAutoCreateRowSorter(true);
        MainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        MainScrollPanel.getHorizontalScrollBar().setUnitIncrement(4);
        MainScrollPanel.getViewport().setBackground(Color.decode("#616183"));

        ArrayList<ArrayList> borrowsList = new ArrayList<>();
        String[] columnNames = {"Borrow ID", "Borrow Date", "First Name", "Title"};

        DB_Select.SelectAllBorrows(readerName, borrowsList);

        System.out.println(borrowsList.size());

        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 4; }
            public int getRowCount() { return borrowsList.size(); }
            public Object getValueAt(int row, int col) { return borrowsList.get(row).get(col); }
            public String getColumnName(int index) { return columnNames[index]; }
        };

        MainTable.setModel(dataModel);

        MainTable.getColumnModel().getColumn(0).setMinWidth(60);
        MainTable.getColumnModel().getColumn(0).setMaxWidth(60);

        MainTable.getColumnModel().getColumn(1).setMinWidth(120);
        MainTable.getColumnModel().getColumn(1).setMaxWidth(120);

        MainTable.getColumnModel().getColumn(2).setMinWidth(200);
        MainTable.getColumnModel().getColumn(2).setMaxWidth(300);

    }

    //Method to update the table of debtors
    private void ShowTableOfDebtors() {
        DebtorTable.setAutoCreateRowSorter(true);
        DebtorTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DebtorScrollPanel.getHorizontalScrollBar().setUnitIncrement(4);
        DebtorScrollPanel.getViewport().setBackground(Color.decode("#616183"));

        ArrayList<ArrayList> debtorsList = new ArrayList<>();
        String[] columnNames = {"First Name", "Last Name"};
        int[] columnWidth = {208, 208};

        DB_Select.SelectAllDebtores(debtorsList);

        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 2; }
            public int getRowCount() { return debtorsList.size(); }
            public Object getValueAt(int row, int col) { return debtorsList.get(row).get(col); }
            public String getColumnName(int index) { return columnNames[index]; }
        };

        DebtorTable.setModel(dataModel);

        for (int i = 0; i < columnWidth.length; i++) {
            if (i < DebtorTable.getColumnModel().getColumnCount()) {
                DebtorTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
            } else break;
        }
    }

}
