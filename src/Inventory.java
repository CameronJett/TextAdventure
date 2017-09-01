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
            if (item.getName().toLowerCase().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public String getItemList() {
        String itemList = "";
        for (Item item : allItems) {
            itemList = itemList + item.getName() + "\n";
        }
        return itemList;
    }

    public void removeItem(String itemName) {
        Item itemToRemove = null;
        for (Item item : allItems) {
            if (item.getName().toLowerCase().equals(itemName)) {
                itemToRemove = item;
            }
        }

        if (itemToRemove != null) {
            allItems.remove(itemToRemove);
        }
    }

    public Item getItem(String itemName) {
        for (Item item : allItems) {
            if (item.getName().toLowerCase().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}
