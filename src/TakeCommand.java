public class TakeCommand {
    private Inventory inventory;

    public TakeCommand(Inventory inv) {
        inventory = inv;
    }

    public String getResponse(Interactable entity, String itemName) {
        if (entity.hasItem(itemName)) {
            inventory.addItem(entity.takeItem(itemName));
            return Const.YOU_TOOK + itemName;
        }
        return Const.CANT_TAKE_THAT;
    }
}
