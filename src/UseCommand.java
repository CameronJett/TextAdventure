import java.util.HashMap;
import java.util.Map;

public class UseCommand {
    Map<Item, Room> itemUseLocation;

    public UseCommand() {
        itemUseLocation = new HashMap<>();
    }

    public String get_response(Room room, Inventory inventory, String item) {
        if (!inventory.hasItem(item)) {
            return Const.DONT_HAVE_THAT_ITEM;
        } else {
            for (Item key : itemUseLocation.keySet()) {
               if (key.getName().equals(item)) {
                   if (itemUseLocation.get(key).equals(room)) {
                       inventory.removeItem(item);
                       return Const.YOU_USED_THE_ITEM + item;
                   }
               }
            }
        }

        return Const.CANT_USE_THERE;
    }

    public void add_item_use_location(Room room, Item item) {
        itemUseLocation.put(item, room);
    }
}
