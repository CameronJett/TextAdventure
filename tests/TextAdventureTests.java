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
    private Inventory inventory = new Inventory();
    private LookCommand look = new LookCommand(inventory);
    private MoveCommand move = new MoveCommand();
    private TextParser parser = new TextParser();
    private Load XMLLoader = new XMLLoader(parser);

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
        assertEquals(new Dialog(Const.TEST_DESCRIPTION), look.getResponse(testRoom, testRoom.getName()));
    }

    @Test
    public void WhenYouAddMultipleRoomsYouCanGetAllDescriptions() {
        assertEquals(new Dialog(Const.TEST_DESCRIPTION), look.getResponse(testRoom, testRoom.getName()));
        assertEquals(new Dialog(Const.SECOND_DESCRIPTION), look.getResponse(secondRoom, secondRoom.getName()));
    }

    @Test
    public void WhenYouLookAtACharacterYouGetTheirDescription() {
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        thirdRoom.addPerson(testPerson);
        assertEquals(new Dialog(Const.TEST_PERSON_DESCRIPTION), look.getResponse(thirdRoom, testPerson.getName()));
    }

    @Test
    public void WhenYouLookAtSomethingInterestingInTheRoomYouGetADescription() {
        testRoom.addPointOfInterest(Const.TEST_OBJECT, new Dialog(Const.TEST_OBJECT_DESCRIPTION));
        assertEquals(new Dialog(Const.TEST_OBJECT_DESCRIPTION), look.getResponse(testRoom, Const.TEST_OBJECT));
    }

    @Test
    public void WhenYouTalkToSomeoneYouGetTheirDialogOptions() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.addDialog(Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        thirdRoom.addPerson(testPerson);
        Dialog testDialog = new Dialog("1. dialog option");
        assertEquals(testDialog, talk.getResponse(thirdRoom, Const.TEST_NAME));
    }

    @Test
    public void WhenYouOnlyTypeMoveYouGetAllExits() {
        testRoom.addExit(secondRoom);
        testRoom.addExit(thirdRoom);
        MoveCommand move = new MoveCommand();
        Dialog testDialog = new Dialog();
        testDialog.addLineOfDialog("1. second room");
        testDialog.addLineOfDialog("2. third room");
        assertEquals(testDialog, move.getResponse(testRoom, Const.MOVE));
    }

    @Test
    public void WhenYouMoveToAPossibleExitItBecomesTheCurrentRoom() {
        testRoom.addExit(secondRoom);
        assertEquals(new Dialog("You moved to second room."), move.getResponse(currentRoom, Const.SECOND_ROOM));
        currentRoom = move.moveToNewRoom(currentRoom, Const.SECOND_ROOM);
        assertEquals(currentRoom.getName(), Const.SECOND_ROOM);
    }

    @Test
    public void WhenYouMoveToARoomThatDoesNotExistYouDoNotMove() {
        testRoom.addExit(thirdRoom);
        assertEquals(new Dialog(Const.CANT_MOVE_THERE), move.getResponse(currentRoom, Const.SECOND_ROOM));
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
        assertEquals(new Dialog(Const.HELP_RESPONSE), help.getResponse(currentRoom, ""));
    }

    @Test
    public void WhenYouEnterARoomForTheFirstTimeYouCanGetEntranceDialog() {
        Dialog testEntrance = new Dialog(Const.TEST_ENTRANCE_DIALOG);
        testRoom.createIntroductionDialog(testEntrance);
        assertEquals(testEntrance, testRoom.getIntroductionDialog());
    }

    /*
    @Test
    public void YouCanCreateAGameFromATextFile() {
        Game myGame = new Game();
        myGame.load(Const.TEST_FILE_NAME, simpleLoader);
        //Should write a function to validate game but I'm going to assume if current room is set then it's good
        assertEquals(true, myGame.getCurrentRoom().getName().equals("Bedroom"));
    }

    @Test
    public void YouCanCreateAGameWithRoomsFromATextFile() {
        Game myGame = new Game();
        myGame.load(Const.TEST_FILE_WITH_ROOMS_NAME, simpleLoader);
        assertEquals(true, myGame.getCurrentRoom().getName().equals("Bedroom"));
    }

    @Test
    public void YouCanCreateAGameWithPeopleFromATextFile() {
        Game myGame = new Game();
        myGame.load(Const.TEST_FILE_WITH_PEOPLE_NAME, simpleLoader);
        assertEquals(true, myGame.getCurrentRoom().getName().equals("Bedroom"));
    }
    */

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
        myGame.load(Const.SIMPLE_GAME_LOAD_FILE_XML, XMLLoader);
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
        testPerson.addItemDialog(testItem.getName(), new Dialog(Const.TEST_SHOW_ITEM_RESPONSE));
        assertEquals(new Dialog(Const.TEST_SHOW_ITEM_RESPONSE), show.getResponse(testRoom, testItem.getName()));
    }

    @Test
    public void APersonsNoItemDialogCanBeCustomized() {
        ShowCommand show = new ShowCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.changeNoItemDialog(new Dialog(Const.TEST_NO_ITEM_DIALOG));
        testRoom.addPerson(testPerson);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        assertEquals(new Dialog(Const.TEST_NO_ITEM_DIALOG), show.getResponse(testRoom, testItem.getName()));
    }

    @Test
    public void WhenAnItemIsUsedItIsTakenOutOfYourInventory() {
        inventory = new Inventory();
        UseCommand use = new UseCommand(inventory);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        testItem.addUseLocation(testRoom);
        use.getResponse(testRoom, Const.TEST_ITEM);
        assertEquals(false, inventory.hasItem(Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedInTheWrongRoomItTellsYouNothingHappened() {
        inventory = new Inventory();
        UseCommand use = new UseCommand(inventory);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        assertEquals(new Dialog(Const.CANT_USE_THERE), use.getResponse(testRoom, Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedInTheCorrectPlaceItTellsYouItWasUsed() {
        inventory = new Inventory();
        UseCommand use = new UseCommand(inventory);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        testItem.addUseLocation(testRoom);
        assertEquals(new Dialog(Const.YOU_USED_THE_ITEM + testItem.getName()), use.getResponse(testRoom, Const.TEST_ITEM));
    }

    @Test
    public void WhenAnItemIsUsedItCanAddANewExit() {
        inventory = new Inventory();
        UseCommand use = new UseCommand(inventory);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        inventory.addItem(testItem);
        testItem.addUseLocation(testRoom);
        testItem.addExitAfterUse(thirdRoom);
        assertEquals(false, testRoom.hasExit(thirdRoom.getName()));
        use.getResponse(testRoom, Const.TEST_ITEM);
        assertEquals(true, testRoom.hasExit(thirdRoom.getName()));
    }

    @Test
    public void WhenYouShowSomeoneAnItemTheyCanGetNewDialog() {
        ShowCommand show = new ShowCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.changeNoItemDialog(new Dialog(Const.TEST_NO_ITEM_DIALOG));
        testRoom.addPerson(testPerson);
        Item testItem = new Item(Const.TEST_ITEM, Const.TEST_ITEM_DESCRIPTION);
        show.item_add_dialog(testItem, testPerson, new DialogWithChoices(Const.DIALOG_OPTION, new Dialog(Const.DIALOG_TEXT)));
        show.getResponse(testRoom, testItem.getName());
        assertEquals(new Dialog("1. " + Const.DIALOG_OPTION), testPerson.getDialogChoices());
    }

    @Test
    public void APersonsDialogCanAddAHiddenDialogAfterTalking() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        DialogWithChoices hiddenDialog = new DialogWithChoices();
        hiddenDialog.addDialogPair(Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        hiddenDialog.addHiddenDialog(new DialogWithChoices(Const.HIDDEN_DIALOG, new Dialog(Const.DIALOG_TEXT)));
        testPerson.addDialog(hiddenDialog);
        secondRoom.addPerson(testPerson);
        Dialog testDialog = new Dialog("1. " + Const.DIALOG_OPTION);
        assertEquals(testDialog, testPerson.getDialogChoices());
        talk.getResponse(secondRoom, Const.DIALOG_OPTION);
        testDialog.addLineOfDialog("2. " + Const.HIDDEN_DIALOG);
        assertEquals(testDialog, testPerson.getDialogChoices());
    }

    /*
    @Test
    public void YouCanCreateAGameWithPeopleItemsHiddenDialogFromATextFile() {
        Game myGame = new Game();
        myGame.load(Const.TEST_FILE_WITH_ITEMS_AND_HIDDEN_DIALOG, simpleLoader);
        assertEquals(true, myGame.getCurrentRoom().getName().equals("Bedroom"));
    }
    */

    @Test
    public void YouCanChooseToTalkBasedOnNumber() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        testPerson.addDialog(Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        secondRoom.addPerson(testPerson);
        assertEquals(new Dialog(Const.DIALOG_TEXT), talk.getResponse(secondRoom, "1"));
    }

    @Test
    public void YouCanLoadAGameWrittenInXML() {
        Game myGame = new Game();
        assertEquals(true, myGame.load(Const.SIMPLE_GAME_LOAD_FILE_XML));
        assertEquals(true, myGame.getCurrentRoom().getName().equals("Bedroom"));
    }

    @Test
    public void APersonsDialogCanBeReplacedAfterTalking() {
        TalkCommand talk = new TalkCommand();
        Person testPerson = new Person(Const.TEST_NAME, Const.TEST_PERSON_DESCRIPTION);
        DialogWithChoices replaceDialog = new DialogWithChoices();
        replaceDialog.addDialogPair(Const.DIALOG_OPTION, Const.DIALOG_TEXT);
        replaceDialog.addReplacementDialog(new DialogWithChoices(Const.HIDDEN_DIALOG, new Dialog(Const.DIALOG_TEXT)));
        testPerson.addDialog(replaceDialog);
        secondRoom.addPerson(testPerson);
        Dialog testDialog = new Dialog("1. " + Const.DIALOG_OPTION);
        assertEquals(testDialog, testPerson.getDialogChoices());
        talk.getResponse(secondRoom, Const.DIALOG_OPTION);
        testDialog.clear();
        testDialog.addLineOfDialog("1. " + Const.HIDDEN_DIALOG);
        assertEquals(testDialog, testPerson.getDialogChoices());
    }

    @Test
    public void TwoDialogsCanBeConcatenatedTogether() {
        Dialog firstHalf = new Dialog(Const.DIALOG_FIRST_HALF);
        Dialog secondHalf = new Dialog(Const.DIALOG_SECOND_HALF);
        Dialog testDialog = new Dialog(Const.DIALOG_FIRST_HALF);
        testDialog.addLineOfDialog(Const.DIALOG_SECOND_HALF);
        assertEquals(testDialog, firstHalf.add(secondHalf));
    }
}
