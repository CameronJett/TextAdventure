import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
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

    public Game() {
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        allItems = new ArrayList<>();
        inventory = new Inventory();

        parser = new TextParser();
        help = new HelpCommand();
        look = new LookCommand();
        move = new MoveCommand();
        show = new ShowCommand();
        take = new TakeCommand(inventory);
        talk = new TalkCommand();
        use = new UseCommand();

        parser = new TextParser();
        String[] commands = {"help", "look", "move", "show", "take", "talk", "use", "quit", "inventory"};
        for (String command : commands) {
            parser.addCommand(command);
        }
    }

    public Game(TextParser p) {
        parser = p;
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        allItems = new ArrayList<>();
    }

    public void run() {
        Scanner reader = new Scanner(System.in);
        print("Hello and welcome.");

        parser.addObject("1");
        parser.addObject("2");

        boolean quit = false;
        while (!quit) {
            parser.parse(reader.nextLine());
            switch (parser.getCommand()) {
                case "help":
                    print(help.getResponse());
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
                    print(use.getResponse(currentRoom, inventory, parser.getObject()));
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

    public boolean load(String fileName) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));

            String line;
            String[] tokens;
            String delim = "::";
            while ((line = reader.readLine()) != null) {
                tokens = line.split(delim);
                switch (tokens[0]) {
                    case "Rooms": //create all rooms
                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);

                            Room room = new Room(tokens[0], tokens[1]);
                            allRooms.add(room);
                            parser.addObject(tokens[0]);

                            //the first room in the file is the starting room
                            if (currentRoom == null) {
                                currentRoom = room;
                            }
                        }
                        break;
                    case "People": //create all people
                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);

                            Person person = new Person(tokens[0], tokens[1]);
                            allPeople.add(person);
                            parser.addObject(tokens[0]);
                        }
                        break;
                    case "Items": //create all items
                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);

                            Item item = new Item(tokens[0], tokens[1]);
                            allItems.add(item);
                            parser.addObject(tokens[0]);
                        }
                        break;

                    case "Room": //load all info of each room
                        String name = reader.readLine();
                        Room room = getRoom(name);

                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);
                            switch (tokens[0]) {
                                case "Exit":
                                    Room exit = getRoom(tokens[1]);
                                    room.addExit(exit);
                                    break;
                                case "Entrance":
                                    List<String> entranceDialog = new ArrayList<>();
                                    entranceDialog.addAll(Arrays.asList(tokens).subList(1, tokens.length));
                                    room.createIntroductionDialog(entranceDialog);
                                    break;
                                case "Interest":
                                    room.addPointOfInterest(tokens[1], tokens[2]);
                                    parser.addObject(tokens[1]);
                                    break;
                                case "Person":
                                    Person person = getPerson(tokens[1]);
                                    room.addPerson(person);
                                    break;
                                case "Item":
                                    Item item = getItem(tokens[1]);
                                    room.addItem(item);
                                    break;
                            }
                        }
                        break;
                    case "Person": //load all info of each person
                        name = reader.readLine();
                        Person person = getPerson(name);

                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);
                            boolean hidden = false;
                            switch (tokens[0]) {
                                case "Hidden":
                                    hidden = true;
                                case "Dialog":
                                    if (tokens.length > 3) {
                                        //Character for hidden dialog link exists
                                        if (hidden) {
                                            person.addHiddenDialog(tokens[3].charAt(0), tokens[1], tokens[2]);
                                        } else {
                                            person.addDialog(tokens[1], tokens[2], tokens[3].charAt(0));
                                        }
                                    } else {
                                        person.addDialog(tokens[1], tokens[2]);
                                    }
                                    break;
                                case "Interest":
                                    person.addPointOfInterest(tokens[1], tokens[2]);
                                    parser.addObject(tokens[1]);
                                    break;
                                case "Item Hold":
                                    Item item = getItem(tokens[1]);
                                    person.addItem(item);
                                    break;
                                case "Item Text":
                                    person.addItemDialog(tokens[1], tokens[2]);
                                    break;
                                case "Item No Text":
                                    person.changeNoItemDialog(tokens[1]);
                                    break;
                            }
                        }
                        break;
                    case "Item": //load all info of each item
                        String itemName = reader.readLine();
                        Item item = getItem(itemName);

                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);
                            boolean addExit = false;
                            switch (tokens[0]) {
                                case "Exit":
                                    addExit = true;
                                case "Use":
                                    String roomName = tokens[1];
                                    Room useRoom = getRoom(roomName);
                                    if (addExit) {
                                        item.addExitAfterUse(useRoom);
                                    } else {
                                        item.addUseLocation(useRoom);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private  void print(String text) {
        //TODO: make it so there is pause in text you have to react to
        System.out.println(text);
    }

    private Item getItem(String itemName) {
        Item returnItem = null;
        for (Item item : allItems) {
            if (item.getName().equals(itemName)) {
                returnItem = item;
            }
        }
        return returnItem;
    }

    private Person getPerson(String personName) {
        Person returnPerson = null;
        for (Person person : allPeople) {
            if (person.getName().equals(personName)) {
                returnPerson = person;
            }
        }
        return returnPerson;
    }

    private Room getRoom(String roomName) {
        Room returnRoom = null;
        for (Room room : allRooms) {
            if (room.getName().equals(roomName)) {
                returnRoom = room;
            }
        }
        return returnRoom;
    }
}
