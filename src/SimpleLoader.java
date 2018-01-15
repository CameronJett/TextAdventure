import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleLoader implements Load{

    @Override
    public boolean load(Game game, TextParser parser, String fileName) {
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
                            game.addRoom(room);
                            parser.addObject(tokens[0]);

                            //the first room in the file is the starting room
                            if (game.getCurrentRoom() == null) {
                                game.setCurrentRoom(room);
                            }
                        }
                        break;
                    case "People": //create all people
                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);

                            Person person = new Person(tokens[0], tokens[1]);
                            game.addPerson(person);
                            parser.addObject(tokens[0]);
                        }
                        break;
                    case "Items": //create all items
                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);

                            Item item = new Item(tokens[0], tokens[1]);
                            game.addItem(item);
                            parser.addObject(tokens[0]);
                        }
                        break;

                    case "Room": //load all info of each room
                        String name = reader.readLine();
                        Room room = game.getRoom(name);

                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);
                            switch (tokens[0]) {
                                case "Exit":
                                    Room exit = game.getRoom(tokens[1]);
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
                                    Person person = game.getPerson(tokens[1]);
                                    room.addPerson(person);
                                    break;
                                case "Item":
                                    Item item = game.getItem(tokens[1]);
                                    room.addItem(item);
                                    break;
                            }
                        }
                        break;
                    case "Person": //load all info of each person
                        name = reader.readLine();
                        Person person = game.getPerson(name);

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
                                    Item item = game.getItem(tokens[1]);
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
                        Item item = game.getItem(itemName);

                        while (!(line = reader.readLine()).equals("")) {
                            tokens = line.split(delim);
                            boolean addExit = false;
                            switch (tokens[0]) {
                                case "Exit":
                                    addExit = true;
                                case "Use":
                                    String roomName = tokens[1];
                                    Room useRoom = game.getRoom(roomName);
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
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
