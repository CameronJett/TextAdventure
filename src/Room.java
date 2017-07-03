import java.util.ArrayList;
import java.util.List;

public class Room {
    String roomName;
    List<Room> exit;

    public Room(String name) {
        roomName = name;
        exit = new ArrayList<>();
    }

    public void AddExit(Room room) {
        exit.add(room);
    }

    public List<Room> getExits() {
        return exit;
    }
}
