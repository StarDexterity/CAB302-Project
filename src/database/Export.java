package database;

import maze.enums.ImageType;
import maze.data.Maze;

import java.util.ArrayList;

/**
 *  This static class can export one or more Default.Maze objects in a number of image file formats.
 */
public class Export {
    /**
     * Exports a list of @{@link Maze} objects to a given folder
     * @param mazes The @{@link Maze} objects to be exported
     * @param filepath The filepath where the mazes will be exported to
     * @param hasSolution Whether the images will have a solution line
     * @param imageType Which filetype the mazes will be exported as
     */
    //TODO: Never Used, Delete?
    public static void export(ArrayList<Maze> mazes, String filepath, boolean hasSolution, ImageType imageType) {

    }

    /**
     * Transforms given @{@link Maze} object into an exportable image object.
     * Image object is unknown at the moment, so this method returns void.
     */
    //TODO: Never Used, Delete?
    public static void printImage(Maze maze) {

    }
}
