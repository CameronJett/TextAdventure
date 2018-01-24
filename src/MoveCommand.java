public class MoveCommand implements Command {

    @Override
    public Dialog getResponse(Room room, String option) {
        if (option.equalsIgnoreCase(Const.MOVE)) {
            return room.getExitChoices();
        } else if (room.hasExit(option)) {
            return (new Dialog(Const.YOU_MOVED + option + ".").add(room.getExit(option).getIntroductionDialog()));
        }

        return new Dialog(Const.CANT_MOVE_THERE);
    }

    //TODO: public Dialog getResponse(Room room, int option) {}

    Room moveToNewRoom(Room room, String option) {
        return room.getExit(option);
    }
}
