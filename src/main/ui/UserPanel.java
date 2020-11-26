package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class displaying the GUI of a usersAccount
public class UserPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private boolean displayKeys = false;
    private boolean setKey = false;
    private boolean addNewKey = false;
    private boolean saveProfile = false;
    JButton viewKeysButton = new JButton(new AbstractAction("View Keys") {
        @Override
        // MODIFIES: this
        // EFFECTS: sets displayKeys boolean field to true
        public void actionPerformed(ActionEvent e) {
            displayKeys = true;
            setKey = false;
            addNewKey = false;
            saveProfile = false;
        }
    });
    private JLabel label;
    JButton setKeypairButton = new JButton(new AbstractAction("Set Keypair") {
        @Override
        // MODIFIES: this
        // EFFECTS: sets setKey boolean field to true
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = true;
            addNewKey = false;
            saveProfile = false;

        }
    });
    JButton addNewCipherObjButton = new JButton(new AbstractAction("Add New Keypair") {
        @Override
        // MODIFIES: this
        // EFFECTS: sets addnewKey boolean field to true
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = false;
            addNewKey = true;
            saveProfile = false;
        }
    });
    // button for Save Account
    JButton saveAccountButton = new JButton(new AbstractAction("Save Profile") {
        // MODIFIES: this
        // EFFECTS: sets saveProfile boolean field to true
        @Override
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = false;
            addNewKey = false;
            saveProfile = true;
        }
    });
    private JFormattedTextField amountField;


    public UserPanel() {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Options");

        viewKeysButton.addActionListener(this::actionPerformed);
        setKeypairButton.addActionListener(this::actionPerformed);
        addNewCipherObjButton.addActionListener(this::actionPerformed);
        saveAccountButton.addActionListener(this::actionPerformed);
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setLayout(new GridLayout());
        addButtons();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("User Dashboard");
        frame.pack();
        frame.setVisible(true);
        frame.add(new JLabel(new ImageIcon("./data/keyimage.jpg")));

    }

    // EFFECTS: Returns a boolean field if an if statement applies to input
    public boolean getAction(String action) {
        if (action.equals("displayKeys")) {
            return displayKeys;
        } else if (action.equals("setKey")) {
            return setKey;
        } else if (action.equals("addNewKey")) {
            return addNewKey;
        } else if (action.equals("saveProfile")) {
            return saveProfile;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Adds buttons to the gui panel
    public void addButtons() {
        panel.add(viewKeysButton);
        panel.add(setKeypairButton);
        panel.add(addNewCipherObjButton);
        panel.add(saveAccountButton);
    }

    public void reset() {
        displayKeys = false;
        setKey = false;
        addNewKey = false;
        saveProfile = false;

    }


    // EFFECTS: actionperformed placeholder
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
