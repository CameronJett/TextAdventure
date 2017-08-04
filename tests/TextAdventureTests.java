import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TextAdventureTests {
    private Room testRoom = new Room(Const.TEST_ROOM, Const.TEST_DESCRIPTION);
    private Room currentRoom = testRoom;
    private Room secondRoom = new Room(Const.SECOND_ROOM, Const.SECOND_DESCRIPTION);
    private Room thirdRoom = new Room(Const.THIRD_ROOM, Const.THIRD_DESCRIPTION);
    private LookCommand look = new LookCommand();
    private MoveCommand move = new MoveCommand();
    private TextParser parser = new TextParser();

    @Before
    public void Init() {
        parser.addCommand(Const.MOVE);
        parser.addObject(Const.KITCHEN);
    }

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
        assertEquals(Const.TEST_DESCRIPTION, look.getResponse(testRoom, testRoom.getName()));
    }

    @Test
    public void WhenYouAddMultipleRoomsYouCanGetAllDescriptions() {
        assertEquals(Const.TEST_DESCRIPTION, look.getResponse(testRoom, testRoom.getName()));
        assertEquals(Const.SECOND_DESCRIPTION, look.getResponse(secondRoom, secondRoom.getName()));
    }

    @Test
    public void WhenYouLookAtACharacterYouGetTheirDescription() {
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        assertEquals(Const.TEST_PERSON_DESCRIPTION, look.getResponse(testPerson, testPerson.getName()));
    }

    @Test
    public void WhenYouLookAtSomethingInterestingInTheRoomYouGetADescription() {
        testRoom.addPointOfInterest(Const.TEST_OBJECT, Const.TEST_OBJECT_DESCRIPTION);
        assertEquals(Const.TEST_OBJECT_DESCRIPTION, look.getResponse(testRoom, Const.TEST_OBJECT));
    }

    @Test
    public void WhenYouTalkToSomeoneYouGetTheirDialogOptions() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.addDialog(Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        assertEquals("1. dialog option\n", talk.getResponse(testPerson, Const.TEST_NAME));
    }

    @Test
    public void WhenYouOnlyTypeMoveYouGetAllExits() {
        testRoom.addExit(secondRoom);
        testRoom.addExit(thirdRoom);
        MoveCommand move = new MoveCommand();
        assertEquals("1. second room\n2. third room\n", move.getResponse(testRoom, Const.MOVE));
    }

    @Test
    public void WhenYouMoveToAPossibleExitItBecomesTheCurrentRoom() {
        testRoom.addExit(secondRoom);
        assertEquals("You moved to second room.", move.getResponse(currentRoom, Const.SECOND_ROOM));
        currentRoom = move.moveToNewRoom(currentRoom, Const.SECOND_ROOM);
        assertEquals(currentRoom.getName(), Const.SECOND_ROOM);
    }

    @Test
    public void WhenYouMoveToARoomThatDoesNotExistYouDoNotMove() {
        testRoom.addExit(thirdRoom);
        assertEquals(Const.CANT_MOVE_THERE, move.getResponse(currentRoom, Const.SECOND_ROOM));
        currentRoom = move.moveToNewRoom(currentRoom, Const.SECOND_ROOM);
        assertEquals(currentRoom.getName(), Const.TEST_ROOM);
    }

    @Test
    public void ParsingAStringWithMoveReturnsAMoveCommand() {
        String input = Const.MOVE;
        parser.parse(input);
        assertEquals(Const.MOVE, parser.getCommand());
    }

    @Test
    public void ParsingAStringWithMoveAndRoomReturnsBoth() {
        String input = "move kitchen";
        parser.parse(input);
        assertEquals(Const.MOVE, parser.getCommand());
        assertEquals(Const.KITCHEN, parser.getObject());
    }

    @Test
    public void ParsingTextWithUpperOrLowercaseReturnsProperly() {
        String input = "mOvE";
        parser.parse(input);
        assertEquals(Const.MOVE, parser.getCommand());
    }

    @Test
    public void WhenYouTypeHelpYouGetHelpInformation() {
        HelpCommand help = new HelpCommand();
        assertEquals(Const.HELP_RESPONSE, help.getResponse());
    }

    @Test
    public void WhenYouEnterARoomForTheFirstTimeYouCanGetEntranceDialog() {
        List<String> testEntrance = new ArrayList<>();
        testEntrance.add(Const.TEST_ENTRANCE_DIALOG);
        testRoom.createEntranceDialog(testEntrance);
        assertEquals(testEntrance, testRoom.getEntranceDialog());
    }

    @Test
    public void YouCanCreateAGameFromATextFile() {
        Game myGame = new Game();
        assertEquals(true, myGame.load(Const.TEST_FILE_NAME));
    }

    @Test
    public void YouCanCreateAGameWithRoomsFromATextFile() {
        Game myGame = new Game();
        assertEquals(true, myGame.load(Const.TEST_FILE_WITH_ROOMS_NAME));
    }

    @Test
    public void YouCanCreateAGameWithPeopleFromATextFile() {
        Game myGame = new Game();
        assertEquals(true, myGame.load(Const.TEST_FILE_WITH_PEOPLE_NAME));
    }

    @Test
    public void APersonCanInhabitARoom() {
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testRoom.addPerson(testPerson);
        assertEquals(testPerson, testRoom.getPerson());
    }

    @Test
    public void APersonCanLeaveARoom() {
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testRoom.addPerson(testPerson);
        testRoom.removePerson();
        assertEquals(null, testRoom.getPerson());
    }
}
