package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI application class for the Ulock program
public class LoginWindow extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private boolean logRequest;
    private boolean createAccount = false;

    JButton newAccount = new JButton(new AbstractAction("New Account") {
        @Override
        //MDOFIES: this
        //EFFECTS: Changes the boolean field createAccount to true and closes gui window
        public void actionPerformed(ActionEvent e) {
            createAccount = true;
            logRequest = false;
            panel.setVisible(false);
            frame.setVisible(false);
        }
    });

    JButton loadAccount = new JButton(new AbstractAction("Load Account") {
        @Override
        // MODIFIES: this
        // EFFECTS: Changes the boolean field logRequest to true and closes the gui window
        public void actionPerformed(ActionEvent e) {
            createAccount = false;
            logRequest = true;
            panel.setVisible(false);
            frame.setVisible(false);
        }
    });


    // Constructs application window
    public LoginWindow() throws Exception {
        frame = new JFrame();
        panel = new JPanel();

        label = new JLabel("Login");

        // button operations
        newAccount.addActionListener(this::actionPerformed);
        loadAccount.addActionListener(this::actionPerformed);

        panel.setBorder(BorderFactory.createEmptyBorder(500, 500, 500, 500));
        panel.setLayout(new GridLayout());
        panel.add(newAccount);
        panel.add(loadAccount);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ULock Login");
        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: returns boolean login field
    public boolean requestLogin() {
        return logRequest;

    }

    //EFFECTS : returns createAccoutn field
    public boolean requestNewAccount() {
        return createAccount;
    }


    @Override
    //EFFECTS: placeholder for actionformed
    public void actionPerformed(ActionEvent e) {

    }


}
