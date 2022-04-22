import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MazeFrame("Hello world!"));
    }

    private JScrollPane scrollPane;
    private JPanel sideBar;

    // Sections in the options sidebar
    private JPanel displayOptions;
    private JPanel regenerateMenu;
    private JPanel SolveStatus;
    private JPanel SaveMenu;


    public MazeFrame(String s) {
        super(s);
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // panel related code will go here
        Container c = this.getContentPane();

        // green for testing purposes
        sideBar = createPanel(Color.GREEN);
        //scrollPane = new JScrollPane(sideBar, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        c.add(sideBar, BorderLayout.WEST);

        // layouts
        SideBarLayout();

        pack();
        setVisible(true);
    }

    private JPanel createPanel(Color c) {
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private JScrollPane createScrollPane() {
        JScrollPane pane = new JScrollPane();
        return pane;
    }

    private JButton createButton(String str) {
        JButton button = new JButton();
        button.setText(str);
        button.addActionListener(this);
        return button;
    }

    private JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        return checkBox;
    }

    // PROBLEMS HERE
    private void SideBarLayout() {
        // layout setup
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));

        // buttons work well with the box layout, this is the behaviour I am looking for
        sideBar.add(new JButton("Hello world1"));
        sideBar.add(new JButton("Hello world1"));

        // FIX: panels split the available area evenly,
        // instead they should only take up the minimum required area of their components
        // and should be anchored to the top.
        displayOptions = createPanel(Color.WHITE);
        regenerateMenu = createPanel(Color.WHITE);
        SolveStatus = createPanel(Color.WHITE);
        SaveMenu = createPanel(Color.WHITE);

        sideBar.add(displayOptions);
        sideBar.add(regenerateMenu);
        //sideBar.add(SolveStatus);
        //sideBar.add(SaveMenu);

        displayOptionsLayout();
        RegenerateLayout();

    }


    private void displayOptionsLayout() {
        GridBagLayout layout = new GridBagLayout();
        //displayOptions.setLayout(layout);
        displayOptions.setAlignmentY(0f);
        // lots of layout code here
        GridBagConstraints constraints = new GridBagConstraints();
        // defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 100;
        constraints.weighty = 100;

        JLabel displayHeader = new JLabel("Display Options");
        JCheckBox showSolution = createCheckBox("Show Solution");
        JCheckBox displayGrid = createCheckBox("Display Grid");

        // FIX: these shouldn't be in a line
        addToPanel(displayOptions, displayHeader, constraints,0,0,1,1);
        addToPanel(displayOptions, showSolution, constraints,0,1,1,1);
        addToPanel(displayOptions, displayGrid, constraints,0,2,1,1);
    }

    private void RegenerateLayout() {
        GridBagLayout layout = new GridBagLayout();
        displayOptions.setLayout(layout);
        // lots of layout code here
        GridBagConstraints constraints = new GridBagConstraints();
        // defaults
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 100;
        constraints.weighty = 100;

        JLabel regenerateHeader = new JLabel("Regenerate");

        addToPanel(regenerateMenu, regenerateHeader, constraints,0,0,1,1);
        addToPanel(regenerateMenu, regenerateHeader, constraints,0,1,1,1);
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        // cool code goes here
    }
}

