import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private List<Item> allItems;

    public Inventory() {
        allItems = new ArrayList<>();
    }
    public void addItem(Item item) {
        allItems.add(item);
    }

    public boolean hasItem(String itemName) {
        for (Item item : allItems) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }
}
