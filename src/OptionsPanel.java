import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class OptionsPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okbtn;
    private JList ageList;
    private JLabel ageLabel;
    private JComboBox empCombo;
    private JCheckBox checkCitizen;
    private JLabel UIDLabel;
    private JTextField UIDField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private ButtonGroup genderGroup;


    public OptionsPanel(){
        Dimension dim  = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);

        nameLabel = new JLabel("Name");
        occupationLabel = new JLabel("Occupation");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        ageList = new JList();
        ageLabel = new JLabel("Age");
        empCombo = new JComboBox();
        checkCitizen = new JCheckBox();
        UIDField = new JTextField(10);
        UIDLabel = new JLabel("UID No");


        nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nameLabel.setLabelFor(nameField);
        //Radio Button
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();

        maleRadio.setActionCommand("male");
        femaleRadio.setActionCommand("female");
        maleRadio.setSelected(true);

        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);



        // UID number
        UIDLabel.setEnabled(false);
        UIDField.setEnabled(false);

        checkCitizen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean isSelected = checkCitizen.isSelected();
                UIDField.setText("");
                UIDField.setEnabled(isSelected);
                UIDLabel.setEnabled(isSelected);
            }
        });


        // model for List
        DefaultListModel ageModel = new DefaultListModel();

        ageList.setModel(ageModel);

        dim = ageList.getPreferredSize();
        dim.width=110;
        ageList.setPreferredSize(dim);
        ageList.setSelectedIndex(1);

        //model for ComboBox

        DefaultComboBoxModel empModel = new DefaultComboBoxModel();
        empModel.addElement("employed");
        empModel.addElement("self-employed");
        empModel.addElement("unemployed");
        empCombo.setModel(empModel);

        okbtn = new JButton("OK");

        Border innerBorder = BorderFactory.createTitledBorder(" Add Person ");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents()
    {
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        // All Field
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        // First Row
        gc.gridy = 0;


        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(nameLabel,gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(nameField,gc);


        // Second Row
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(occupationLabel,gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(occupationField,gc);



        // Third Row
        gc.gridy++;

        gc.gridx=0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(ageLabel,gc);

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(ageList,gc);


        //Forth Row
        gc.gridy++;

        gc.gridx=0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(new JLabel("Employment : "),gc);

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(empCombo,gc);

        //Fifth Row
        gc.gridy++;

        gc.gridx=0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(new JLabel("Indian : "),gc);

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(checkCitizen,gc);


        //Sixth Row
        gc.gridy++;

        gc.gridx=0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(UIDLabel,gc);

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(UIDField,gc);


        //Seventh Row
        gc.gridy++;
        gc.weighty = 0.05;

        gc.gridx=0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(new JLabel("Gender : "),gc);

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(maleRadio,gc);
        //Eighth Row
        gc.gridy++;
        gc.weighty = 0.1;

        gc.gridx =1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(femaleRadio,gc);
        //Nineth Row
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 1;

        gc.insets = new Insets(0,5,0,0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okbtn,gc);
    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h) {
        addToPanel(jp, c, constraints, x, y, w, h, null);
    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h, Insets i) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        if (i != null) {
            constraints.insets = i;
        }
        jp.add(c, constraints);
    }

}