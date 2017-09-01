import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interactable extends GameEntity{
    private boolean firstIntroduction;
    private List<String> introductionDialog;
    private Map<String, String> pointsOfInterest;
    private Item item;

    public Interactable(String tempName, String tempDescription) {
        super(tempName, tempDescription);
        firstIntroduction = false;
        introductionDialog = new ArrayList<>();
        pointsOfInterest = new HashMap<>();
    }

    public void createIntroductionDialog(List<String> dialog) {
        firstIntroduction = true;
        introductionDialog.clear();
        introductionDialog.addAll(dialog);
    }

    public List<String> getIntroductionDialog() {
        if (firstIntroduction) {
            firstIntroduction = false;
            return introductionDialog;
        }
        return new ArrayList<>();
    }

    public void addPointOfInterest(String object, String description) {
        pointsOfInterest.put(object.toLowerCase(), description);
    }

    public boolean contains(String object) {
        return pointsOfInterest.containsKey(object);
    }

    public String getPointOfInterest(String object) {
        return pointsOfInterest.get(object);
    }

    public void addItem(Item tempItem) {
        item = tempItem;
    }

    public boolean hasItem(String itemName) {
        return item.getName().toLowerCase().equals(itemName);
    }

    public Item takeItem(String itemName) {
        if (item.getName().toLowerCase().equals(itemName)) {
            Item tempItem = item;
            item = null;
            return tempItem;
        }
        return null;
    }
}
