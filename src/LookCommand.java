import java.util.HashMap;
import java.util.Map;

public class LookCommand {
     private Map<Interactable, String> entities = new HashMap<>();

    public String GetResponse(Interactable entity) {
        if(entities.containsKey(entity))
        {
            return entities.get(entity);
        }
        return "That's not very interesting.";
    }

    public void Put(Interactable entity, String description) {
        entities.put(entity, description);
    }
}
