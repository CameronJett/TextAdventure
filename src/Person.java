import java.util.Map;

public class Person extends Interactable{
    private CharacterDialog dialog;

    public Person(String name, String description) {
        super(name, description);
        dialog = new CharacterDialog();
    }

    public void addDialog(String option, String response) { dialog.add(option, response, Const.NULL_CHAR); }

    public void addDialog(Map<String, String> dialogToAdd) { dialog.addAll(dialogToAdd); }

    public void addDialog(String option, String response, Character hiddenLink) {
        dialog.add(option, response, hiddenLink);
    }

    public void addHiddenDialog(Character hiddenLink, String option, String response) {
        dialog.addHidden(hiddenLink, option, response);
    }

    public boolean hasDialog(String option) {
        return dialog.hasDialog(option);
    }

    public boolean hasDialog(int i) { return dialog.getDialogChoices().length() >= i; }

    public String getDialog(String option) { return dialog.getDialog(option).toLowerCase(); }

    public String getDialog(int i) { return dialog.getDialog(i); }

    public String getDialogChoices() { return dialog.getDialogChoices(); }

    public void addItemDialog(String item, String response) { dialog.addItemDialog(item, response); }

    public String getItemDialog(String item) { return dialog.getItemDialog(item).toLowerCase(); }

    public void changeNoItemDialog(String tempDialog) { dialog.changeNoItemDialog(tempDialog); }
}
