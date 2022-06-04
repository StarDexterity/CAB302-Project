package ui.pages.editpage.options.image;

import maze.data.Position;
import ui.pages.editpage.options.cell.CellDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class InsertImage extends JPanel {

    public Position selectedCell;
    public static BufferedImage newImg;

    public static File img;

    public void setSelectedCell(Position cell) {
        this.selectedCell = cell;
    }

    public InsertImage() {

    }

    FileFilter imageFilter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes());
    public BufferedImage getImage(){
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(imageFilter);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            img = fc.getSelectedFile();
        }

        try {
            newImg = ImageIO.read(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        newImg = resize(newImg,10,10);
        return newImg;
    }

    public BufferedImage resize(BufferedImage image, int newW, int newH){
        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB); //HARDCODED SIZES FOR NOW

        Graphics g = newImage.createGraphics();
        g.drawImage(image, 0, 0, newW, newH, null);
        g.dispose();
        return newImage;
    }

}



