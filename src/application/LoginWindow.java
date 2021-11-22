package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static application.Main.frame;


public class LoginWindow extends JDialog {
    private JPanel contentPane;
    private JPanel Header;
    private JLabel Logo;
    private JTextField LoginField;
    private JTextField PassField;
    private JTextPane errorMessageTextPane;

    LoginWindow() {
        setContentPane(contentPane);
        frame.setSize(700, 500);
        setModal(true);

        //set logo's image
        ImageIcon newImg = new ImageIcon("src/resources/images/Logo.png");
        Logo.setBounds(0, 0, 166, 125);
        newImg.setImage(newImg.getImage().getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH));
        Logo.setIcon(newImg);

        // call OnOk() on ENTER
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnOk();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void OnOk() {

        if (LoginField.getText().isEmpty() || PassField.getText().isEmpty()){
            errorMessageTextPane.setText("Some fields might be empty");
            return;
        }
        //Compare entered password with stored in database
        PasswordSystem passSys = new PasswordSystem();
        boolean login = passSys.TryToLogin(LoginField.getText(), PassField.getText());

        //If login data is correct - show MainMenu window in the center of screen
        if (login) {
            frame.setVisible(false);

            frame.setSize(1240, 800);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
            frame.setContentPane(new MainMenu().getContentPane());

            frame.setVisible(true);

        } else {
            //Show error message and refresh password field
            PassField.setText("");
            errorMessageTextPane.setText("Incorrect login or password");
        }
    }

}
