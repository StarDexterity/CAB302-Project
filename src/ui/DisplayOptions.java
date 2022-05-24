package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayOptions extends JPanel {
    private JCheckBox showSolution;
    private JCheckBox showGrid;

    private JButton colorButton;

    private EditPage editPage;

    public DisplayOptions(EditPage editPage) {
        this.editPage = editPage;

        // create show solution check box and bind event
        showSolution = UIHelper.createCheckBox("Show Solution");
        showSolution.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowSolution(src.isSelected());
        });

        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color changeColor = JColorChooser.showDialog(null, "Change Color",Color.RED);
                if (changeColor != null){
                    //TESTING..
                    colorButton.setBackground(changeColor);
                }
            }
        });

        // create show grid check box and bind event
        showGrid = UIHelper.createCheckBox("Show Grid");
        showGrid.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowGrid(src.isSelected());
        });

        Border innerBorder = BorderFactory.createTitledBorder("Display options");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,5,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();

        GridBagHelper.addToPanel(this, showSolution, gbc, 0, 0, 1, 1);
        GridBagHelper.addToPanel(this, showGrid, gbc, 0, 1, 1, 1);
        GridBagHelper.addToPanel(this, colorButton, gbc, 0,2,1,1);
    }
}
