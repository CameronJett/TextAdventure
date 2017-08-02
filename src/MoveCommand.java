public class MoveCommand {

    public String getResponse(Room room) {
        return room.getExitChoices();
    }
}
