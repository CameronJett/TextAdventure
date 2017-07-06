import java.util.HashMap;
import java.util.Map;

public class Person extends Interactable{
    Map<String, String> dialog;

    public Person(String name, String description) {
        super(name, description);
        dialog = new HashMap<>();
    }

    public void addDialog(String option, String response) {
        dialog.put(option, response);
    }

    public boolean hasDialog(String option) {
        return dialog.containsKey(option);
    }

    public String getDialog(String option) {
        return dialog.get(option);
    }

    public String getDialogChoices() {
        String dialogChoices = "";

        int i = 1;
        for (String key : dialog.keySet()) {
            dialogChoices = dialogChoices + i + ". " + key + "\n";
            i++;
        }
        return dialogChoices;
    }
}
