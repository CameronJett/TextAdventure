import java.util.HashMap;
import java.util.Map;

public class Person extends Interactable{
    private CharacterDialog dialog;

    public Person(String name, String description) {
        super(name, description);
        dialog = new CharacterDialog();
    }

    public void addDialog(String option, String response) { dialog.add(option, response); }

    public void addDialog(Map<String, String> dialogToAdd) { dialog.addAll(dialogToAdd); }

    public boolean hasDialog(String option) {
        return dialog.hasDialog(option);
    }

    public String getDialog(String option) {
        return dialog.getDialog(option);
    }

    public String getDialogChoices() { return dialog.getDialogChoices(); }

    public void addItemDialog(String item, String response) { dialog.addItemDialog(item, response); }

    public String getItemDialog(String item) { return dialog.getItemDialog(item); }

    public void changeNoItemDialog(String tempDialog) { dialog.changeNoItemDialog(tempDialog); }
}
