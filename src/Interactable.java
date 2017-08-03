import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interactable {
    private String name;
    private String description;
    private Map<String, String> pointsOfInterest;
    private boolean firstEntrance;
    private List<String> entranceDialog;

    public Interactable(String tempName, String tempDescription) {
        name = tempName;
        description = tempDescription;
        pointsOfInterest = new HashMap<>();
        firstEntrance = false;
        entranceDialog = new ArrayList<>();
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

    public void createEntranceDialog(List<String> dialog) {
        firstEntrance = true;
        entranceDialog.clear();
        entranceDialog.addAll(dialog);
    }

    public List<String> getEntranceDialog() {
        if (firstEntrance) {
            firstEntrance = false;
            return entranceDialog;
        }
        return new ArrayList<>();
    }
}
