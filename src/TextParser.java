import java.util.ArrayList;
import java.util.List;

public class TextParser {
    private String command = "";
    private String object = "";
    private List<String> possibleCommands;
    private List<String> possibleObjects;

    public TextParser() {
        possibleCommands = new ArrayList<>();
        possibleObjects = new ArrayList<>();
    }

    public void parse(String input) {
        command = "";
        object = "";
        input = input.toLowerCase();

        for (int i=0; possibleCommands.size()>i; i++) {
            if (input.contains(possibleCommands.get(i))) {
                command = possibleCommands.get(i);
            }
        }
        for (int i=0; possibleObjects.size()>i; i++) {
            if (input.contains(possibleObjects.get(i))) {
                object = possibleObjects.get(i);
            }
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
