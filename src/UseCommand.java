import java.util.HashMap;
import java.util.Map;

public class UseCommand {
    private Map<Item, Room> itemUseLocation;
    private Map<Item, Room> itemAddExit;

    public UseCommand() {
        itemUseLocation = new HashMap<>();
        itemAddExit = new HashMap<>();
    }

    public String get_response(Room room, Inventory inventory, String item) {
        if (!inventory.hasItem(item)) {
            return Const.DONT_HAVE_THAT_ITEM;
        } else {
            for (Item key : itemUseLocation.keySet()) {
               if (key.getName().equals(item)) {
                   if (itemUseLocation.get(key).equals(room)) {
                       String additionalDialog = use_item(key, room);
                       inventory.removeItem(item);
                       return Const.YOU_USED_THE_ITEM + item + additionalDialog;
                   }
               }
            }
        }

        return Const.CANT_USE_THERE;
    }

    public void add_item_use_location(Room room, Item item) {
        itemUseLocation.put(item, room);
    }

    public void item_add_exit(Item item, Room exit) {
        itemAddExit.put(item, exit);
    }

    private String use_item(Item item, Room room) {
        String returnDialog = "";

        //if the item adds an exit to the room
        if (itemAddExit.containsKey(item)) {
            room.addExit(itemAddExit.get(item));
            //TODO: map of additional dialog for item use
        }

        return returnDialog;
    }
}
