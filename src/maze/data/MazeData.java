package maze.data;

import java.util.Date;

public class MazeData {

    private int id;
    private String author;
    private String title;
    private String description;
    //TODO: Final
    private Date creationDate;
    private Date lastEditDate;

    public MazeData() {
        // Gets current date
        creationDate = new Date();
        // Default
        title = "Unnamed Maze";
    }

    public void updateData(String author) {
        this.author = author;
        // Updates with current date
        lastEditDate = new Date();
    }

    public void assignId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public MazeData title(String title) {
        this.title = title;
        return this;
    }
    public MazeData description(String description) {
        this.description = description;
        return this;
    }

}
