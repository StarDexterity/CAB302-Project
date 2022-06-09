package tests;

import maze.data.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVarious {
    Position position = new Position(3,3);

    @Test
    public void testPositionEquals(){
        Position pos2 = new Position(3,3);
        assertEquals(position.equals(pos2), true);
    }

    @Test
    public void testPositionString(){
        assertEquals(position.toString(),"{x=3, y=3}");
    }
}
