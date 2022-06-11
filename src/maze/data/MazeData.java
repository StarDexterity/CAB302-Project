package maze.data;

import java.time.Instant;

/**
 * Handles the maze properties, such as Author, Title, Description, Creation Date, and Last Edit Date
 */
public class MazeData {

    private int id;
    private String author;
    private String title;
    private String description;
    private Instant creationDate;
    private Instant lastEditDate;

    public MazeData() {
        id = 0;
        author = "Anonymous";
        title = "Unnamed Maze";
        description = "A maze.";

        // Gets current date
        creationDate = Instant.now();
        lastEditDate = Instant.now();
    }

    public MazeData(int id, String author, String title, String description, Instant creationDate, Instant lastEditDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
    }

    /**
     * A function used to update the 'Author' data for a maze
     * @param author A string for the name of the author
     */
    public void updateData(String author) {
        if (author != null) {
            this.author = author;
        }
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

    public void setAuthor(String author) {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {}

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {}

    public Instant getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Instant lastEditDate) {}


    public MazeData title(String title) {
        if (title != null) {
            this.title = title;
        }
        return this;
    }

    public MazeData description(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }

}
