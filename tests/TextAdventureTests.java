import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TextAdventureTests {
    private Room testRoom = new Room("test room name", "test description");
    private Room secondRoom = new Room("second room", "second description");
    private Room thirdRoom = new Room("third room", "third description");
    private LookCommand look = new LookCommand();

    @Test
    public void WhenYouAddAnExitRoomItIsAddedToPossibleMoveLocations() {
        testRoom.addExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        assertEquals(rooms, testRoom.getExits());
    }

    @Test
    public void WhenYouAddTwoExitsTheyAreBothAvailableToMoveTo() {
        testRoom.addExit(secondRoom);
        testRoom.addExit(thirdRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        rooms.add(thirdRoom);
        assertEquals(rooms, testRoom.getExits());
    }

    @Test
    public void WhenYouRemoveAnExitItIsNoLongerAvailable() {
        testRoom.addExit(secondRoom);
        testRoom.removeExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        assertEquals(rooms, testRoom.getExits());
    }

    @Test
    public void WhenYouLookAtARoomYouGetItsDescription() {
        assertEquals("test description", look.GetResponse(testRoom));
    }

    @Test
    public void WhenYouAddMultipleRoomsYouCanGetAllDescriptions() {
        assertEquals("test description", look.GetResponse(testRoom));
        assertEquals("second description", look.GetResponse(secondRoom));
    }

    @Test
    public void WhenYouLookAtACharacterYouGetTheirDescription() {
        Person testPerson = new Person("test name", "test person description");
        assertEquals("test person description", look.GetResponse(testPerson));
    }
}
