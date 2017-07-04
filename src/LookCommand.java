import java.util.HashMap;
import java.util.Map;

public class LookCommand {
    Map<Room, String> rooms = new HashMap<>();

    public String GetResponse(Room room) {
        if(rooms.containsKey(room))
        {
            return rooms.get(room);
        }
        return "That's not very interesting.";
    }

    public void Put(Room room, String description) {
        rooms.put(room, description);
    }
}
