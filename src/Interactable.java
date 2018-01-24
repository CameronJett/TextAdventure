import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interactable extends GameEntity{
    private boolean firstIntroduction;
    private Dialog introductionDialog;
    private Map<String, Dialog> pointsOfInterest;
    private Item item;

    public Interactable(String tempName, Dialog tempDescription) {
        super(tempName, tempDescription);
        firstIntroduction = false;
        introductionDialog = new Dialog();
        pointsOfInterest = new HashMap<>();
    }

    public void createIntroductionDialog(Dialog dialog) {
        firstIntroduction = true;
        introductionDialog.clear();
        introductionDialog = dialog;
    }

    public Dialog getIntroductionDialog() {
        if (firstIntroduction) {
            firstIntroduction = false;
            return introductionDialog;
        }
        return new Dialog();
    }

    public void addPointOfInterest(String object, Dialog description) {
        pointsOfInterest.put(object.toLowerCase(), description);
    }

    public boolean contains(String object) {
        return pointsOfInterest.containsKey(object);
    }

    public Dialog getPointOfInterest(String object) {
        return pointsOfInterest.get(object);
    }

    public void addItem(Item tempItem) {
        item = tempItem;
        addPointOfInterest(item.getName(), item.getDescription());
    }

    public boolean hasItem(String itemName) {
        return item.getName().equalsIgnoreCase(itemName);
    }

    public Item takeItem(String itemName) {
        if (item.getName().equalsIgnoreCase(itemName)) {
            Item tempItem = item;
            item = null;
            return tempItem;
        }
        return null;
    }
}
