import java.util.ArrayList;
import java.util.List;

public class TextParser {
    private String command;
    private String object;
    private List<String> possibleCommands;
    private List<String> possibleObjects;

    public TextParser() {
        command = "";
        object = "";
        possibleCommands = new ArrayList<>();
        possibleObjects = new ArrayList<>();
    }

    public void parse(String input) {
        command = "";
        object = "";
        input = input.toLowerCase();

        for (String possibleCommand : possibleCommands) {
            if (input.contains(possibleCommand)) {
                command = possibleCommand;
            }
        }
        for (String possibleObject : possibleObjects) {
            if (input.contains(possibleObject)) {
                object = possibleObject;
            }
        }

        //this is so you can only type "move" "help" etc.
        if (command.length()>0 && object.length()==0) {
            object = command;
        }
    }

    public String getCommand() {
        return command;
    }

    public String getObject() {
        return object;
    }

    public void addCommand(String command) {
        possibleCommands.add(command);
    }

    public void addObject(String object) {
        possibleObjects.add(object);
    }
}
