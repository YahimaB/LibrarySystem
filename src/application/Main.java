package application;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {

    public static JFrame frame;
    static int CurrentLibrarianID = 0;

    public static void main(String[] args) {

        //Create a blank window with default exit
        frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Set window's size and put it into the center of the screen
        frame.setSize(700, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        //Open login panel in created window
        frame.setContentPane(new LoginWindow().getContentPane());
        frame.setVisible(true);

    }
}
