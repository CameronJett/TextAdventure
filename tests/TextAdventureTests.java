import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TextAdventureTests {
    private Room testRoom = new Room("test room name");
    private Room secondRoom = new Room("second room");
    private Room thirdRoom = new Room("third room");

    @Test
    public void WhenYouAddAnExitRoomItIsAddedToPossibleMoveLocations() {
        testRoom.AddExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        assertEquals(rooms, testRoom.GetExits());
    }

    @Test
    public void WhenYouAddTwoExitsTheyAreBothAvailableToMoveTo() {
        testRoom.AddExit(secondRoom);
        testRoom.AddExit(thirdRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        rooms.add(thirdRoom);
        assertEquals(rooms, testRoom.GetExits());
    }

    @Test
    public void WhenYouRemoveAnExitItIsNoLongerAvailable() {
        testRoom.AddExit(secondRoom);
        testRoom.RemoveExit(secondRoom);
        List<Room> rooms = new ArrayList<>();
        assertEquals(rooms, testRoom.GetExits());
    }

    @Test
    public void WhenYouLookAtARoomYouGetItsDescription() {
        LookCommand look = new LookCommand();
        look.Put(testRoom, "test description");
        assertEquals("test description", look.GetResponse(testRoom));
    }

    @Test
    public void WhenYouAddMultipleRoomsYouCanGetAllDescriptions() {
        LookCommand look = new LookCommand();
        look.Put(testRoom, "test description");
        look.Put(secondRoom, "second description");
        assertEquals("test description", look.GetResponse(testRoom));
        assertEquals("second description", look.GetResponse(secondRoom));
    }

    @Test
    public void WhenYouLookAtACharacterYouGetTheirDescription() {
        Person testPerson = new Person("test name");
        LookCommand look = new LookCommand();
        look.Put(testPerson, "test person description");
        assertEquals("test person description", look.GetResponse(testPerson));
    }
}
