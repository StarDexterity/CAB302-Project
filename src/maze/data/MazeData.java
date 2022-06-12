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

    /**
     * Constructor called when a new {@link Maze} is constructed. Provides default values.
     */
    public MazeData() {
        id = 0;
        author = "Anonymous";
        title = "Unnamed Maze";
        description = "A maze.";

        // Gets current date
        creationDate = Instant.now();
        lastEditDate = Instant.now();
    }

    /**
     * Constructor when creating a {@link MazeData} object from the Database.
     */
    public MazeData(int id, String author, String title, String description, Instant creationDate, Instant lastEditDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
    }

    /**
     * A function used to update the 'Author' data for a maze.
     * Required to initiate the chain of setters based on the Builder design pattern.
     * Updates the lastEditDate with the current time.
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Instant getLastEditDate() {
        return lastEditDate;
    }

    /**
     * Setter based of the Builder design pattern.
     * Sets title and returns the {@link MazeData} object so that functions can be chained
     * @return This {@link MazeData} object
     */
    public MazeData title(String title) {
        if (title != null) {
            this.title = title;
        }
        return this;
    }

    /**
     * Setter based of the Builder design pattern.
     * Sets description and returns the {@link MazeData} object so that functions can be chained
     * @return This {@link MazeData} object
     */
    public MazeData description(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }

}
