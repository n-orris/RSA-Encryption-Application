package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private boolean displayKeys = false;
    private boolean setKey = false;
    private boolean addNewKey = false;
    private boolean saveProfile = false;
    private JLabel label;

    JButton viewKeysButton = new JButton(new AbstractAction("View Keys") {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayKeys = true;
            setKey = false;
            addNewKey = false;
            saveProfile = false;
        }
    });

    JButton setKeypairButton = new JButton(new AbstractAction("Set Keypair") {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = true;
            addNewKey = false;
            saveProfile = false;

        }
    });

    JButton addNewCipherObjButton = new JButton(new AbstractAction("Add New Keypair") {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = false;
            addNewKey = true;
            saveProfile = false;
        }
    });

    JButton saveAccountButton = new JButton(new AbstractAction("Save Profile") {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayKeys = false;
            setKey = false;
            addNewKey = false;
            saveProfile = true;
        }
    });

    public UserPanel() {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Options");

        viewKeysButton.addActionListener(this::actionPerformed);
        setKeypairButton.addActionListener(this::actionPerformed);
        addNewCipherObjButton.addActionListener(this::actionPerformed);
        saveAccountButton.addActionListener(this::actionPerformed);


        panel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        panel.setLayout(new GridLayout());
        addButtons();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("User Dashboard");
        frame.pack();
        frame.setVisible(true);

    }

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

    public void addButtons() {
        panel.add(viewKeysButton);
        panel.add(setKeypairButton);
        panel.add(addNewCipherObjButton);
        panel.add(saveAccountButton);


    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
