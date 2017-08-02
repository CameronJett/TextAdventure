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
        secondRoom.addExit(testRoom);
        secondRoom.removeExit(testRoom);
        List<Room> rooms = new ArrayList<>();
        assertEquals(rooms, secondRoom.getExits());
    }

    @Test
    public void WhenYouLookAtARoomYouGetItsDescription() {
        assertEquals("test description", look.getResponse(testRoom, testRoom.getName()));
    }

    @Test
    public void WhenYouAddMultipleRoomsYouCanGetAllDescriptions() {
        assertEquals("test description", look.getResponse(testRoom, testRoom.getName()));
        assertEquals("second description", look.getResponse(secondRoom, secondRoom.getName()));
    }

    @Test
    public void WhenYouLookAtACharacterYouGetTheirDescription() {
        Person testPerson = new Person("test name", "test person description");
        assertEquals("test person description", look.getResponse(testPerson, testPerson.getName()));
    }

    @Test
    public void WhenYouLookAtSomethingInterestingInTheRoomYouGetADescription() {
        testRoom.addPointOfInterest("object", "objects description");
        assertEquals("objects description", look.getResponse(testRoom, "object"));
    }

    @Test
    public void WhenYouTalkToSomeoneYouGetTheirDialogOptions() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person("test name", "test person desription");
        testPerson.addDialog("dialog option", "dialog text");
        assertEquals("1. dialog option\n", talk.getResponse(testPerson, "test name"));
    }

    @Test
    public void WhenYouOnlyTypeMoveYouGetAllExits() {
        testRoom.addExit(secondRoom);
        testRoom.addExit(thirdRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(secondRoom);
        rooms.add(thirdRoom);
        MoveCommand move = new MoveCommand();
        assertEquals("1. second room\n2. third room\n", move.getResponse(testRoom, "move"));
    }

    @Test
    public void WhenYouMoveToAPossibleExitItBecomesTheCurrentRoom() {
        Room currentRoom = testRoom;
        testRoom.addExit(secondRoom);
        MoveCommand move = new MoveCommand();
        move.getResponse(currentRoom, "second room");
        currentRoom = move.moveToNewRoom(currentRoom, "second room");
        assertEquals(currentRoom.getName(), "second room");
    }
}
