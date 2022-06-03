package ui.pages.editpage.options.image;

import maze.data.Position;
import ui.pages.editpage.options.cell.CellDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InsertImage extends JPanel {

    public Position selectedCell;
    public BufferedImage in;


    public void setSelectedCell(Position cell) {
        this.selectedCell = cell;
    }

    public InsertImage(File image) {
        try {
            in = ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(in);

    }

}



