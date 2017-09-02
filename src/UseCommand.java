public class UseCommand {
    Inventory inventory;

    public UseCommand(Inventory inv) { inventory = inv; }

    public String getResponse(Room room, String item) {
        if (!inventory.hasItem(item)) {
            return Const.DONT_HAVE_THAT_ITEM;
        } else {
            Item useItem = inventory.getItem(item);
            if (useItem.getUseLocation().equals(room)) {
                String additionalDialog = use_item(useItem, room);
                inventory.removeItem(item);
                return Const.YOU_USED_THE_ITEM + item + additionalDialog;
            }
        }

        return Const.CANT_USE_THERE;
    }

    private String use_item(Item item, Room room) {
        String returnDialog = "";

        //if the item adds an exit to the room
        if (item.addsExit()) {
            room.addExit(item.getExit());
            //TODO: map of additional dialog for item use
        }

        return returnDialog;
    }
}
