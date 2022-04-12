import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Template extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Template("Hello world!"));
    }

    public Template(String s) {
        super(s);
    }

    private void createGUI() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignore
        }

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // panel related code will go here


        pack();
        setVisible(true);
    }

    private JPanel createPanel(Color c) {
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private JButton createButton(String str) {
        JButton button = new JButton();
        button.setText(str);
        button.addActionListener(this);
        return button;
    }



    /**
     *
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width of the component
     * @param h the grid height of the component
     */
    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h) {
        addToPanel(jp, c, constraints, x, y, w, h, new Insets(0,0,0,0));
    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h, Insets i) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        constraints.insets = i;
        jp.add(c, constraints);
    }

    @Override
    public void run() {
        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        // cool code goes here
    }
}

