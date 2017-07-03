import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private List<Room> exits;

    public Room(String name) {
        roomName = name;
        exits = new ArrayList<>();
    }

    public void AddExit(Room room) {
        exits.add(room);
    }

    public List<Room> GetExits() {
        return exits;
    }

    public void RemoveExit(Room room) {
        if(exits.contains(room)) {
            exits.remove(room);
        }
    }
}
