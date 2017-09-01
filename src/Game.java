import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Room currentRoom;
    private List<Room> allRooms;
    private List<Person> allPeople;
    private List<Item> allItems;

    private TextParser parser;

    public Game() {
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        allItems = new ArrayList<>();
        parser = new TextParser();
    }

    public Game(TextParser p) {
        this();
        parser = p;
    }

    public boolean load(String fileName) {
        //TODO: Check that its a legitimate file?
        //TODO: Clean this up
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                switch (line) {
                    case "Rooms:":
                        //create all rooms
                        while (!(line = reader.readLine()).equals("")) {
                            String name = line.substring(0, line.indexOf(":"));
                            String description = line.substring(line.indexOf(":") + 2);

                            Room room = new Room(name, description);
                            allRooms.add(room);
                            parser.addObject(name);

                            //the first room in the file is the starting room
                            if (currentRoom == null) {
                                currentRoom = room;
                            }
                        }
                        break;
                    case "People:":
                        while (!(line = reader.readLine()).equals("")) {
                            String name = line.substring(0, line.indexOf(":"));
                            String description = line.substring(line.indexOf(":") + 2);

                            Person person = new Person(name, description);
                            allPeople.add(person);
                            parser.addObject(name);
                        }
                        break;
                    case "Items:":
                        while (!(line = reader.readLine()).equals("")) {
                            String name = line.substring(0, line.indexOf(":"));
                            String description = line.substring(line.indexOf(":") + 2);

                            Item item = new Item(name, description);
                            allItems.add(item);
                            parser.addObject(name);
                        }
                        break;
                    case "Room:":
                        //load all info of each room
                        line = reader.readLine();

                        //get the room
                        Room room = getRoom(line);
                        while (!(line = reader.readLine()).equals("")) {
                            switch (line.substring(0, line.indexOf(":"))) {
                                case "Exit":
                                    String exitName = line.substring(line.indexOf(":") + 2);
                                    Room exit = getRoom(exitName);
                                    room.addExit(exit);
                                    break;
                                case "Entrance":
                                    line = line.substring(line.indexOf(":") + 2);
                                    List<String> entranceDialog = new ArrayList<>();
                                    while (line.contains(":")) {
                                        String dialog = line.substring(0, line.indexOf(":"));
                                        entranceDialog.add(dialog);
                                        line = line.substring(line.indexOf(":") + 2);
                                    }
                                    entranceDialog.add(line);
                                    room.createIntroductionDialog(entranceDialog);
                                    break;
                                case "Interest":
                                    line = line.substring(line.indexOf(":") + 2);
                                    String object = line.substring(0, line.indexOf(":"));
                                    String description = line.substring(line.indexOf(":") + 2);
                                    room.addPointOfInterest(object, description);
                                    parser.addObject(object);
                                    break;
                                case "Person":
                                    String personName = line.substring(line.indexOf(":") + 2);
                                    Person person = getPerson(personName);
                                    room.addPerson(person);
                                    break;
                            }
                        }
                        break;
                    case "Person:":
                        String name = reader.readLine();
                        Person person = getPerson(name);
                        while (!(line = reader.readLine()).equals("")) {
                            boolean hidden = false;
                            switch (line.substring(0, line.indexOf(":"))) {
                                case "Hidden":
                                    hidden = true;
                                case "Dialog":
                                    line = line.substring(line.indexOf(":")+2);
                                    String dialogOption = line.substring(0, line.indexOf(":"));
                                    String dialogText = line.substring(line.indexOf(":") + 2);
                                    if (dialogText.contains(":")) {
                                        Character link = dialogText.charAt(dialogText.length()-1);
                                        dialogText = dialogText.substring(0, dialogText.indexOf(":"));
                                        if (hidden) {
                                            person.addHiddenDialog(link, dialogOption, dialogText);
                                        } else {
                                            person.addDialog(dialogOption, dialogText, link);
                                        }
                                    } else {
                                        person.addDialog(dialogOption, dialogText);
                                    }
                                    break;
                                case "Interest":
                                    line = line.substring(line.indexOf(":") + 2);
                                    String object = line.substring(0, line.indexOf(":"));
                                    String description = line.substring(line.indexOf(":") + 2);
                                    person.addPointOfInterest(object, description);
                                    parser.addObject(object);
                                    break;
                                case "Item Hold":
                                    String itemName = line.substring(line.indexOf(":") + 2);
                                    Item item = getItem(itemName);
                                    person.addItem(item);
                                    break;
                                case "Item Text":
                                    line = line.substring(line.indexOf(":") + 2);
                                    itemName = line.substring(0, line.indexOf(":"));
                                    String itemDescription = line.substring(line.indexOf(":") + 2);
                                    person.addItemDialog(itemName, itemDescription);
                                    break;
                                case "Item No Text":
                                    line = line.substring(line.indexOf(":") + 2);
                                    person.changeNoItemDialog(line);
                                    break;
                            }
                        }
                        break;
                    case "Item:":
                        String itemName = reader.readLine();
                        Item item = getItem(itemName);
                        while (!(line = reader.readLine()).equals("")) {
                            boolean addExit = false;
                            switch (line.substring(0, line.indexOf(":"))) {
                                case "Exit:":
                                    addExit = true;
                                case "Use:":
                                    String roomName = line.substring(line.indexOf(":") + 2);
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
