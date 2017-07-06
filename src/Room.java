import java.util.ArrayList;
import java.util.List;

public class Room extends Interactable{
    private List<Room> exits;

    public Room(String name) {
        super(name);
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
