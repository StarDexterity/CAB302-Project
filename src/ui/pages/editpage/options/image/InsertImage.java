package ui.pages.editpage.options.image;

import maze.data.Maze;
import maze.data.Position;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class InsertImage extends JPanel {

    public static Position imageCell;
    public static Position bottomRight;
    public static BufferedImage newImg;

    public static ArrayList<BufferedImage> images;

    public static ArrayList<Position> imageTopLeft;

    public static ArrayList<Position> imageBottomRight;

    public static File img;

    public static Maze currentMaze;

    public static int imageCount = -1;

    //TODO:Delete?
    public void setImageCell(Position cell) {
        this.imageCell = cell;
    }

    public InsertImage() {
        images = new ArrayList<>();
        imageTopLeft = new ArrayList<>();
        imageBottomRight = new ArrayList<>();
    }

    FileFilter imageFilter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes());

    //TODO: Never Used, Delete?
    public BufferedImage getImage(){
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(imageFilter);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            img = fc.getSelectedFile();
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            return null;
        }

        try {
            newImg = ImageIO.read(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int x1 = imageCell.getX();
        int y1 = imageCell.getY();

        //HARDCODED FOR TESTING
        int x2 = bottomRight.getX();
        int y2 = bottomRight.getY();

        int xFinal = (x2-x1)*25+25;
        int yFinal = (y2-y1)*25+25;


        newImg = resize(newImg,xFinal,yFinal);
        removeCells(x1, x2, y1, y2);

        //Will be used to add multiple images, and to help with removing
        images.add(newImg);
        imageTopLeft.add(imageCell);
        imageBottomRight.add(bottomRight);
        imageCount++;

        return newImg;
    }

    public BufferedImage resize(BufferedImage image, int newW, int newH){
        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics g = newImage.createGraphics();
        g.drawImage(image, 0, 0, newW, newH, null);
        g.dispose();
        return newImage;
    }

    //function to border out affected cells
    public Maze removeCells(int x1, int x2, int y1, int y2){
        int[][] x = currentMaze.getMazeGrid();
        for (int i = x1; i<=x2; i++){
            for (int j = y1; j<=y2; j++){
                currentMaze.setCellEnabled(i,j,false);

            }
        }
        currentMaze.setMazeGrid(x);
        return currentMaze;
    }

    //TODO: Never Used, Delete?
    public Maze resetPassable(Position topLeft, Position bottomRight){
        int x1 = topLeft.getX();
        int y1 = topLeft.getY();
        int x2 = bottomRight.getX();
        int y2 = bottomRight.getY();
        int[][] x = currentMaze.getMazeGrid();
        for (int i = x1; i<=x2; i++){
            for (int j = y1; j<=y2; j++){

                currentMaze.setCellEnabled(i,j,true);

            }
        }
        currentMaze.setMazeGrid(x);
        return currentMaze;
    }


    public static void newMazeClear(){
        images.clear();
        imageTopLeft.clear();
        imageBottomRight.clear();

    }

}



