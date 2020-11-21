package ui;

import model.Account;
import model.CipherObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserKeysPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JLabel label;
    private Account account;
    private JTextArea textField;
    private List<CipherObj> cipherObjs;


    public UserKeysPanel(Account account) {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Options");
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


    public void postKeys() {
        if (account.getCiphers() == null) {
            textField.setLineWrap(true);
            textField.setColumns(2);
            textField.setText("You Do Not Have any Keys");
        } else {
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


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
