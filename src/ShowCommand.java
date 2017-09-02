import java.util.HashMap;
import java.util.Map;

public class ShowCommand {
    private Map<Map<Item, Person>, Map<String, String>> itemAddDialog;

    public ShowCommand() {
        itemAddDialog = new HashMap<>();
    }

    public String getResponse(Room room, String item) {
        if (room.hasPerson()) {
            show_item(item, room.getPerson());
            return room.getPerson().getItemDialog(item);
        }
        return Const.NO_ONE_TO_SHOW;
    }

    public void item_add_dialog(Item item, Person person, String dialogOption, String dialogText) {
        Map<Item, Person> key = new HashMap<>();
        Map<String, String> value = new HashMap<>();

        key.put(item, person);
        value.put(dialogOption, dialogText);

        itemAddDialog.put(key, value);
    }

    private void show_item(String item, Person person) {
        Map<Item, Person> showKey = new HashMap<>();

        for (Map<Item, Person> keys: itemAddDialog.keySet()) {
            for (Item it: keys.keySet()) {
                if (it.getName().equalsIgnoreCase(item)) {
                    showKey.put(it, keys.get(it));
                }
            }
        }

        if (itemAddDialog.containsKey(showKey)) {
            person.addDialog(itemAddDialog.get(showKey));
        }
    }
}
