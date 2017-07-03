import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoomTests {
    Room testRoom = new Room("test room name");

    @Test
    public void WhenYouAddAnExitRoomItIsAddedToPossibleMoveLocations() {
        Room secondRoom = new Room("second room");
        testRoom.AddExit(secondRoom);
        assertEquals(secondRoom, testRoom.getExits());
    }
}
