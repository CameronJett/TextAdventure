import java.util.HashMap;
import java.util.Map;

public class Interactable {
    private String name;
    private String description;
    private Map<String, String> pointsOfInterest;

    public Interactable(String tempName, String tempDescription) {
        name = tempName;
        description = tempDescription;
        pointsOfInterest = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void addPointOfInterest(String object, String description) {
        pointsOfInterest.put(object, description);
    }

    public boolean contains(String object) {
        return pointsOfInterest.containsKey(object);
    }

    public String getPointOfInterest(String object) {
        return pointsOfInterest.get(object);
    }
}
