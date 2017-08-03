import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Room currentRoom;
    private List<Room> allRooms;

    public Game() {
        allRooms = new ArrayList<>();
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
                                    room.createEntranceDialog(entranceDialog);
                                    break;
                                case "Interest":
                                    line = line.substring(line.indexOf(":") + 2);
                                    String object = line.substring(0, line.indexOf(":"));
                                    String description = line.substring(line.indexOf(":") + 2);
                                    room.addPointOfInterest(object, description);
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
