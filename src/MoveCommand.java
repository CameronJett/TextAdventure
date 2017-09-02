public class MoveCommand {

    public String getResponse(Room room, String option) {
        if (option.equalsIgnoreCase(Const.MOVE)) {
            return room.getExitChoices();
        } else if (room.hasExit(option)) {
            return Const.YOU_MOVED + option + ".";
        }

        return Const.CANT_MOVE_THERE;
    }

    public Room moveToNewRoom(Room room, String option) {
        return room.getExit(option);
    }
}
