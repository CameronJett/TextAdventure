import java.util.ArrayList;
import java.util.List;

public class Room extends Interactable{
    private List<Room> exits;
    private Person person;

    public Room(String name, Dialog description) {
        super(name, description);
        exits = new ArrayList<>();
        person = null;
    }

    public Room(String name, String description) {
        this(name, new Dialog(description));
    }

    public void addExit(Room room) {
        exits.add(room);
    }

    public boolean hasExit(String option) {
        for (Room room : exits) {
            if (room.getName().equalsIgnoreCase(option)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasExit(int option) {
        return exits.size() >= option;
    }

    public Room getExit(String option) {
        Room exit = this;
        for (Room room : exits) {
            if (room.getName().equalsIgnoreCase(option)) {
                exit = room;
            }
        }
        return exit;
    }

    public Room getExit(int option) {
        Room exit = this;

        if (exits.size() >= option) {
            return exits.get(option-1);
        }
        return exit;
    }

    public List<Room> getExits() {
        return exits;
    }

    public void removeExit(Room room) {
        if(exits.contains(room)) {
            exits.remove(room);
        }
    }

    public Dialog getExitChoices() {
        StringBuilder dialogChoices = new StringBuilder();
        Dialog returnDialog = new Dialog();

        for (int i=1; i <= exits.size(); i++) {
            dialogChoices.append(i).append(". ").append(exits.get(i - 1).getName());
            returnDialog.addLineOfDialog(dialogChoices.toString());
            dialogChoices.setLength(0);
        }
        return returnDialog;
    }

    public void addPerson(Person p) {
        person = p;
    }

    public Person getPerson() {
        return person;
    }

    public void removePerson() {
        person = null;
    }

    public boolean hasPerson() {
        return person != null;
    }
}
