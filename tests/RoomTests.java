import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RoomTests {
    Room testRoom = new Room("test room name");

    @Test
    public void WhenYouAddAnExitRoomItIsAddedToPossibleMoveLocations() {
        Room secondRoom = new Room("second room");
        testRoom.AddExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        assertEquals(rooms, testRoom.GetExits());
    }

    @Test
    public void WhenYouAddTwoExitsTheyAreBothAvailableToMoveTo() {
        Room secondRoom = new Room("second room");
        Room thirdRoom = new Room("third room");
        testRoom.AddExit(secondRoom);
        testRoom.AddExit(thirdRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        rooms.add(thirdRoom);
        assertEquals(rooms, testRoom.GetExits());
    }

    @Test
    public void WhenYouRemoveAnExitItIsNoLongerAvailable() {
        Room secondRoom = new Room("second room");
        testRoom.AddExit(secondRoom);
        testRoom.RemoveExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        assertEquals(rooms, testRoom.GetExits());
    }
}
