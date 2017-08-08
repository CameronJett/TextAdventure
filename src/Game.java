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

    private TextParser parser;

    public Game() {
        allRooms = new ArrayList<>();
        allPeople = new ArrayList<>();
        parser = new TextParser();
    }

    public Game(TextParser p) {
        this();
        parser = p;
    }

    public boolean load(String fileName) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                //load rooms
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
                            }
                        }
                        break;
                    case "Person:":
                        //get name and description
                        line = reader.readLine();

                        String name = line.substring(0, line.indexOf(":"));
                        String desc = line.substring(line.indexOf(":") + 2);
                        Person person = new Person(name, desc);
                        allPeople.add(person);
                        parser.addObject(name);
                        while (!(line = reader.readLine()).equals("")) {
                            switch (line.substring(0, line.indexOf(":"))) {
                                case "Dialog":
                                    line = line.substring(line.indexOf(":")+2);
                                    String dialogOption = line.substring(0, line.indexOf(":"));
                                    String dialogText = line.substring(line.indexOf(":") + 2);
                                    person.addPointOfInterest(dialogOption, dialogText);
                                    break;
                                case "Interest":
                                    line = line.substring(line.indexOf(":") + 2);
                                    String object = line.substring(0, line.indexOf(":"));
                                    String description = line.substring(line.indexOf(":") + 2);
                                    person.addPointOfInterest(object, description);
                                    parser.addObject(object);
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
