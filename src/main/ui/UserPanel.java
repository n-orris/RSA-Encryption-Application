package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JMenuBar jmenu;
    private JLabel label;

    JButton viewKeys = new JButton(new AbstractAction("View Keys") {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("button1 test");

        }
    });


    public UserPanel() {
        frame = new JFrame();
        panel = new JPanel();
        jmenu = new JMenuBar();
        button1 = new JButton("View keys");
        button2 = new JButton("Create Account");
        button3 = new JButton("Set Keyset");
        button4 = new JButton("Add new Keyset");
        button5 = new JButton("Save Account");

        label = new JLabel("Options");

        viewKeys.addActionListener(this::actionPerformed);


        panel.setBorder(BorderFactory.createEmptyBorder(200, 300, 200, 300));
        panel.setLayout(new GridLayout());
        addButtons();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ULock");
        frame.pack();
        frame.setVisible(true);

    }

    public void addButtons() {
        panel.add(viewKeys);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);

    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
