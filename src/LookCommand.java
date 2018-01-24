public class LookCommand implements Command {
    private Inventory inventory;

    LookCommand(Inventory inv) { inventory = inv; }

    @Override
    public Dialog getResponse(Room room, String name) {
        if (room.getName().equalsIgnoreCase(name) || name.equalsIgnoreCase(Const.LOOK)) {
            return room.getDescription();
        } else if (room.contains(name)) {
            return room.getPointOfInterest(name);
        } else if (room.hasPerson()) {
            if (room.getPerson().getName().equalsIgnoreCase(name)) {
                return room.getPerson().getDescription();
            } else if (room.getPerson().contains(name)) {
                return room.getPerson().getPointOfInterest(name);
            }
        } else if (inventory.hasItem(name)) {
            return inventory.getItem(name).getDescription();
        }
        return new Dialog(Const.LOOK_NOT_INTERESTING);
    }
}
