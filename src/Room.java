import java.util.ArrayList;
import java.util.List;

public class Room extends Interactable{
    private List<Room> exits;

    public Room(String name, String description) {
        super(name, description);
        exits = new ArrayList<>();
    }

    public void addExit(Room room) {
        exits.add(room);
    }

    public List<Room> getExits() {
        return exits;
    }

    public void removeExit(Room room) {
        if(exits.contains(room)) {
            exits.remove(room);
        }
    }

    public String getExitChoices() {
        String dialogChoices = "";

        for (int i=1; i <= exits.size(); i++) {
            dialogChoices = dialogChoices + i + ". " + exits.get(i-1).getName() + "\n";
        }
        return dialogChoices;
    }
}
