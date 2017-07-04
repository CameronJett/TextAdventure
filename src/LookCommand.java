
public class LookCommand {
    private Room room;
    private String description;

    public String GetResponse() {
        return description;
    }

    public void Put(Room tempRoom, String tempDescription) {
        room = tempRoom;
        description = tempDescription;
    }
}
