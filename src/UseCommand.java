public class UseCommand {
    public String get_response(Inventory inventory, String item) {
        if (!inventory.hasItem(item)) {
            return "You don't have anything like that.";
        } else {
            inventory.removeItem(item);
            return "You used the " + item;
        }
    }
}
