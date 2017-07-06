import java.util.HashMap;
import java.util.Map;

public class LookCommand {
     private Map<Interactable, String> rooms = new HashMap<>();

    public String GetResponse(Interactable entity) {
        if(rooms.containsKey(entity))
        {
            return rooms.get(entity);
        }
        return "That's not very interesting.";
    }

    public void Put(Interactable entity, String description) {
        rooms.put(entity, description);
    }
}
