import java.util.ArrayList;
import java.util.List;

public class Room extends Interactable{
    private List<Room> exits;
    private Person person;

    public Room(String name, String description) {
        super(name, description);
        exits = new ArrayList<>();
        person = null;
    }

    public void addExit(Room room) {
        exits.add(room);
    }

    public boolean hasExit(String option) {
        for (Room room : exits) {
            if (room.getName().equals(option)) {
                return true;
            }
        }
        return false;
    }

    public Room getExit(String option) {
        Room exit = this;
        for (Room room : exits) {
            if (room.getName().equals(option)) {
                exit = room;
            }
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

    public String getExitChoices() {
        StringBuilder dialogChoices = new StringBuilder();

        for (int i=1; i <= exits.size(); i++) {
            dialogChoices.append(i).append(". ").append(exits.get(i - 1).getName()).append("\n");
        }
        return dialogChoices.toString();
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
