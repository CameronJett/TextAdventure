public class ShowCommand {
    public String getResponse(Room room, String item) {
        if (room.hasPerson()) {
            return room.getPerson().getItemDialog(item);
        }
        return "There is no one to show that to.";
    }
}
