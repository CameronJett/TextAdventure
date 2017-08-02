import java.util.List;

public class MoveCommand {

    public String getResponse(Room room, String option) {
        if (option.equals("move")) {
            return room.getExitChoices();
        } else if (room.hasExit(option)) {
            return "You moved to " + option + ".";
        }

        return "You can't move there.";
    }

    public Room moveToNewRoom(Room room, String option) {
        return room.getExit(option);
    }
}
