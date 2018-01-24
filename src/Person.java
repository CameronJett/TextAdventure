import java.util.List;

public class Person extends Interactable{
    private CharacterDialog dialog;

    public Person(String name, Dialog description) {
        super(name, description);
        dialog = new CharacterDialog();
    }

    public Person(String name, String description) {
        this(name, new Dialog(description));
    }

    public void addDialog(String option, String response) { dialog.add(option, response); }

    public void addDialog(String option, Dialog response) { dialog.add(option, response); }

    public void addDialog(List<DialogWithChoices> dialogList) { dialog.addAll(dialogList); }

    public void addDialog(DialogWithChoices dialogWithChoices) {
        dialog.add(dialogWithChoices);
    }

    public boolean hasDialog(String option) {
        return dialog.hasDialog(option);
    }

    public boolean hasDialog(int i) { return dialog.hasDialog(i); }

    public Dialog getDialog(String option) { return dialog.getDialog(option); }

    public Dialog getDialog(int i) { return dialog.getDialog(i); }

    public Dialog getDialogChoices() { return dialog.getDialogChoices(); }

    public void addItemDialog(String item, Dialog response) { dialog.addItemDialog(item, response); }

    public Dialog getItemDialog(String item) { return dialog.getItemDialog(item); }

    public void changeNoItemDialog(Dialog tempDialog) { dialog.changeNoItemDialog(tempDialog); }
}
