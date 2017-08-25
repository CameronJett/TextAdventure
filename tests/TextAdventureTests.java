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
        testRoom.createIntroductionDialog(testEntrance);
        assertEquals(testEntrance, testRoom.getIntroductionDialog());
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

    @Test
    public void WhenYouLoadAnObjectItCanBeChosenInTheTextParser() {
        Game myGame = new Game(parser);
        myGame.load(Const.TEST_FILE_WITH_ROOMS_NAME);
        parser.parse("look object");
        assertEquals("object", parser.getObject());
    }

    @Test
    public void ARoomOrPersonCanHoldAnItem() {
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        testRoom.addItem(testItem);
        assertEquals(true, testRoom.hasItem(Const.TEST_ITEM));
    }

    @Test
    public void YouCanTakeAnItemIntoYourInventory() {
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        assertEquals(true, inventory.hasItem(testItem.getName()));
    }

    @Test
    public void AnItemInARoomCanBeTakenIntoYourInventory() {
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        testRoom.addItem(testItem);
        TakeCommand take = new TakeCommand(inventory);
        take.getResponse(testRoom, Const.TEST_ITEM);
        assertEquals(true, inventory.hasItem(Const.TEST_ITEM));
    }

    @Test
    public void YouCanCheckYourInventory() {
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        assertEquals(Const.TEST_ITEM + "\n", inventory.getItemList());
    }

    @Test
    public void AnItemCanBeShownToAPerson() {
        ShowCommand show = new ShowCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testRoom.addPerson(testPerson);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        testPerson.addItemDialog(testItem.getName(), Const.TEST_SHOW_ITEM_RESPONSE);
        assertEquals(Const.TEST_SHOW_ITEM_RESPONSE, show.getResponse(testRoom, testItem.getName()));
    }

    @Test
    public void APersonsNoItemDialogCanBeCustomized() {
        ShowCommand show = new ShowCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.changeNoItemDialog(Const.TEST_NO_ITEM_DIALOG);
        testRoom.addPerson(testPerson);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        assertEquals(Const.TEST_NO_ITEM_DIALOG, show.getResponse(testRoom, testItem.getName()));
    }

    @Test
    public void WhenAnItemIsUsedItIsTakenOutOfYourInventory() {
        UseCommand use = new UseCommand();
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        use.add_item_use_location(testRoom, testItem);
        use.get_response(testRoom, inventory, Const.TEST_ITEM);
        assertEquals(false, inventory.hasItem(Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedInTheWrongRoomItTellsYouNothingHappened() {
        UseCommand use = new UseCommand();
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        assertEquals(Const.CANT_USE_THERE, use.get_response(testRoom, inventory, Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedInTheCorrectPlaceItTellsYouItWasUsed() {
        UseCommand use = new UseCommand();
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        use.add_item_use_location(testRoom, testItem);
        assertEquals(Const.YOU_USED_THE_ITEM + testItem.getName(), use.get_response(testRoom, inventory, Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedItCanAddANewExit() {
        UseCommand use = new UseCommand();
        Inventory inventory = new Inventory();
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        use.add_item_use_location(testRoom, testItem);
        use.item_add_exit(testItem, thirdRoom);
        assertEquals(false, testRoom.hasExit(thirdRoom.getName()));
        use.get_response(testRoom, inventory, Const.TEST_ITEM);
        assertEquals(true, testRoom.hasExit(thirdRoom.getName()));
    }

    @Test
    public void WhenYouShowSomeoneAnItemTheyCanGetNewDialog() {
        ShowCommand show = new ShowCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.changeNoItemDialog(Const.TEST_NO_ITEM_DIALOG);
        testRoom.addPerson(testPerson);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        show.item_add_dialog(testItem, testPerson, Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        show.getResponse(testRoom, testItem.getName());
        assertEquals("1. " + Const.DIALOG_OPTION + "\n", testPerson.getDialogChoices());
    }
}
