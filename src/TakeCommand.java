public class TakeCommand implements Command {
    private Inventory inventory;

    TakeCommand(Inventory inv) {
        inventory = inv;
    }

    @Override
    public String getResponse(Room room, String itemName) {
        if (room.hasItem(itemName)) {
            inventory.addItem(room.takeItem(itemName));
            return Const.YOU_TOOK + itemName;
        } else {
            if (room.hasPerson()) {
                if (room.getPerson().hasItem(itemName)) {
                    inventory.addItem(room.takeItem(itemName));
                    return Const.YOU_TOOK + itemName;
                }
            }
        }
        return Const.CANT_TAKE_THAT;
    }
}
