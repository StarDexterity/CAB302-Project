package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel {
    private App app;

    public HomePage(App app) {
        super();
        this.app = app;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JButton btn = new JButton("Press me!");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.nextPage();
            }
        });
        // layout code
        add(btn);
    }
}

