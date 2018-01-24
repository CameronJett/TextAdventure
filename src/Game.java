import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Game {
    private Room currentRoom;
    private List<Room> allRooms;
    private List<Person> allPeople;
    private List<Item> allItems;
    private Inventory inventory;

    private TextParser parser;
    private HelpCommand help;
    private LookCommand look;
    private MoveCommand move;
    private ShowCommand show;
    private TakeCommand take;
    private TalkCommand talk;
    private UseCommand use;

    Game() {
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        allItems = new ArrayList<>();
        inventory = new Inventory();

        parser = new TextParser();
        help = new HelpCommand();
        look = new LookCommand(inventory);
        move = new MoveCommand();
        show = new ShowCommand();
        take = new TakeCommand(inventory);
        talk = new TalkCommand();
        use = new UseCommand(inventory);

        parser = new TextParser();
        String[] commands = {"help", "look", "move", "show", "take", "talk", "use", "quit", "inventory"};
        for (String command : commands) {
            parser.addCommand(command);
        }
    }

    Game(TextParser p) {
        parser = p;
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        allItems = new ArrayList<>();
    }

    void run() {
        Scanner reader = new Scanner(System.in);
        print(currentRoom.getIntroductionDialog());

        //TODO: fix how to chose numbers in parser
        parser.addObject("1");
        parser.addObject("2");
        parser.addObject("3");

        boolean quit = false;
        while (!quit) {
            parser.parse(reader.nextLine());
            switch (parser.getCommand()) {
                case "help":
                    print(help.getResponse(currentRoom, parser.getObject()));
                    break;
                case "look":
                    print(look.getResponse(currentRoom, parser.getObject()));
                    break;
                case "move":
                    print(move.getResponse(currentRoom, parser.getObject()));
                    currentRoom = move.moveToNewRoom(currentRoom, parser.getObject());
                    break;
                case "show":
                    print(show.getResponse(currentRoom, parser.getObject()));
                    break;
                case "take":
                    print(take.getResponse(currentRoom, parser.getObject()));
                    break;
                case "talk":
                    print(talk.getResponse(currentRoom, parser.getObject()));
                    break;
                case "use":
                    print(use.getResponse(currentRoom, parser.getObject()));
                    break;
                case "inventory":
                    print(inventory.getItemList());
                    break;
                case "quit":
                    print("Goodbye!");
                    quit = true;
                    break;
            }
        }
    }

    boolean load(String fileName) {
        Load loader = new XMLLoader(parser);
        return loader.load(this, fileName);
    }

    //putting this here to test different loaders
    boolean load(String fileName, Load loader) {
        return loader.load(this, fileName);
    }

    private void print(String text) {
        //TODO: make it so there is pause in text you have to react to
        System.out.println(text);
    }

    private void print(Dialog dialog) {
        //TODO: print dialog with pauses
        List<String> text = dialog.readDialog();
        for (String aText : text) {
            System.out.println(aText);
        }
    }

    void addItem(Item item) {
        allItems.add(item);
        parser.addObject(item.getName());
    }

    Item getItem(String itemName) {
        Item returnItem = null;
        for (Item item : allItems) {
            if (item.getName().equals(itemName)) {
                returnItem = item;
            }
        }
        return returnItem;
    }

    void addPerson(Person person) {
        allPeople.add(person);
        parser.addObject(person.getName());
    }

    Person getPerson(String personName) {
        Person returnPerson = null;
        for (Person person : allPeople) {
            if (person.getName().equals(personName)) {
                returnPerson = person;
            }
        }
        return returnPerson;
    }

    void addRoom(Room room) {
        allRooms.add(room);
        parser.addObject(room.getName());
    }

    Room getRoom(String roomName) {
        Room returnRoom = null;
        for (Room room : allRooms) {
            if (room.getName().equals(roomName)) {
                returnRoom = room;
            }
        }
        return returnRoom;
    }

    Room getCurrentRoom() {
        return currentRoom;
    }

    void setCurrentRoom(Room room) {
        currentRoom = room;
    }
}
