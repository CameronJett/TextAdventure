
public class Room {
    String roomName;
    Room exit;

    public Room(String name) {
        roomName = name;
    }

    public void AddExit(Room secondRoom) {
        exit = secondRoom;
    }

    public Room getExits() {
        return exit;
    }
}
