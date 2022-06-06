package maze.data;

import java.time.Instant;
import java.util.Date;

public class MazeData {

    private int id;
    private String author;
    private String title;
    private String description;
    private Instant creationDate;
    private Instant lastEditDate;

    public MazeData() {
        id = 0;
        title = "Unnamed Maze";
        author = "Anonymous";
        description = "";

        // Gets current date
        creationDate = Instant.now();
        lastEditDate = new Date();
    }

    public MazeData(int id, String author, String title, String description, Date creationDate, Date lastEditDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
    }

    public void updateData(String author) {
        this.author = author;
        // Updates with current date
        lastEditDate = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }


    // ---- Getters!
    public String getAuthor() {
        return author;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Instant getLastEditDate() {
        return lastEditDate;
    }
    // ----

    public MazeData title(String title) {
        this.title = title;
        return this;
    }
    public MazeData description(String description) {
        this.description = description;
        return this;
    }

}
