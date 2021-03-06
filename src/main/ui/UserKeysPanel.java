package ui;

import model.Account;
import model.CipherObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// A gui window to display the users keys
public class UserKeysPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JLabel label;
    private Account account;
    private JTextArea textField;
    private List<CipherObj> cipherObjs;
    private boolean keys;


    public UserKeysPanel(Account account, boolean keys) {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Options");
        this.keys = keys;
        textField = new JTextArea();
        this.account = account;
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setLayout(new GridLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("User Keys");
        frame.pack();
        frame.setVisible(true);
        postKeys();
        cipherObjs = account.getCiphers();

    }


    //MODIFIES: this
    //EFFECTS: Adds the textfield entries the user's keys to the frame
    public void postKeys() {
        if (!keys) {
            textField.append(account.getAccountCipher().getPublicKey().toString());
            textField.append(account.getAccountCipher().getPrivateKey().toString());
            frame.add(textField);

        } else if (keys) {
            for (CipherObj c : account.getCiphers()) {
                String privKey = c.getPrivateKey().toString();
                if (privKey.contains("SunRsaSign")) {
                    textField.append("\n Public Key:");
                    textField.append("\n" + c.getPublicKey().toString());
                    textField.append("\n" + privKey.substring(0, 67));
                    textField.append("\n" + privKey.substring(68, 685));
                    textField.append("\n" + privKey.substring(706));
                } else {
                    textField.append("\n Public Key:");
                    textField.append("\n" + c.getPublicKey().toString());
                    textField.append("\n" + privKey.substring(0, 56));
                    textField.append("\n" + privKey.substring(57, 674));
                    textField.append("\n" + privKey.substring(695, 1311));
                }
                frame.add(textField);
            }
        }
    }


    // unused default actionperformed method
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
