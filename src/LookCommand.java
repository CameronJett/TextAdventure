public class LookCommand implements Command {
    Inventory inventory;

    public LookCommand(Inventory inv) { inventory = inv; }

    public String getResponse(Room room, String name) {
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
        return Const.LOOK_NOT_INTERESTING;
    }
}
