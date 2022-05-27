package maze;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Export {


    //placeholder export (will replace with maze display)
    public static void exportMaze(String args) throws IOException {

        //placeholders (this should maybe change depending on the maze???)
        int width = 250;
        int height = 250;

        // BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        // fill the image
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        // create a circle
        g2d.setColor(Color.green);
        g2d.fillOval(0, 0, width, height);

        // create a string
        g2d.setColor(Color.blue);
        g2d.drawString("Java Code Geeks", 50, 120);

        // deletes this graphics
        g2d.dispose();

        //could make this a dropdown as user submits???

        // save as PNG
        File file = new File("myimage.png");
        ImageIO.write(bufferedImage, "png", file);

        // save as JPG
        file = new File("myimage.jpg");
        ImageIO.write(bufferedImage, "jpg", file);
    }
}