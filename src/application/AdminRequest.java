package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static application.Main.frame;

public class AdminRequest extends JDialog {
    private JPanel contentPane;
    private JPanel Header;
    private JLabel Logo;
    private JTextField TitleField;
    private JButton OK;
    private JButton Cancel;
    private JTextPane TextField;

    public AdminRequest() {
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
    }

    private void OnOk() {
        //If any field is empty - show alert window, else show success window
        if (TitleField.getText().isEmpty() || TextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Some fields might be empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String subject = TitleField.getText();
            String body = TextField.getText();
            SendMail.SendFromGMail(subject, body);

            JOptionPane.showMessageDialog(frame, "Your message has \nbeen sent successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void OnCancel() {
        dispose();
    }

}
