public class LookCommand {

    public String getResponse(Room room, String name) {
        if (room.getName().equals(name) || name.equals(Const.LOOK)) {
            return room.getDescription();
        } else if (room.contains(name)) {
            return room.getPointOfInterest(name);
        } else if (room.getPerson().getName().equals(name)) {
            return room.getPerson().getDescription();
        } else if (room.getPerson().contains(name)) {
            return room.getPerson().getPointOfInterest(name);
        }
        return Const.LOOK_NOT_INTERESTING;
    }
}
