import java.util.HashMap;
import java.util.Map;

public class ShowCommand implements Command {
    private Map<Map<Item, Person>, DialogWithChoices> itemAddDialog;

    ShowCommand() {
        itemAddDialog = new HashMap<>();
    }

    @Override
    public Dialog getResponse(Room room, String item) {
        if (room.hasPerson()) {
            show_item(item, room.getPerson());
            return room.getPerson().getItemDialog(item);
        }
        return new Dialog(Const.NO_ONE_TO_SHOW);
    }

    void item_add_dialog(Item item, Person person, DialogWithChoices dialog) {
        Map<Item, Person> key = new HashMap<>();
        key.put(item, person);

        itemAddDialog.put(key, dialog);
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
