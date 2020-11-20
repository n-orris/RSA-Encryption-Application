package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI application class for the Ulock program
public class LoginWindow extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JLabel label;
    private boolean logRequest;


    // Constructs application window
    public LoginWindow() throws Exception {
        frame = new JFrame();
        panel = new JPanel();
        button = new JButton("Login");
        button.addActionListener(this);
        label = new JLabel("Login");


        panel.setBorder(BorderFactory.createEmptyBorder(500, 500, 500, 500));
        panel.setLayout(new GridLayout());
        panel.add(button);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ULock");
        frame.pack();
        frame.setVisible(true);
    }

    public boolean requestLogin() {
        return logRequest;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Logging in....");
        logRequest = true;
        panel.setVisible(false);
        frame.setVisible(false);
    }


}
