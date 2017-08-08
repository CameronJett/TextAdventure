import java.util.HashMap;
import java.util.Map;

public class Person extends Interactable{
    private Map<String, String> dialog;
    private Map<String, String> itemDialog;
    private String noItemDialog;

    public Person(String name, String description) {
        super(name, description);
        dialog = new HashMap<>();
        itemDialog = new HashMap<>();
        noItemDialog = Const.DEFAULT_NO_ITEM_DIALOG;
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
        StringBuilder dialogChoices = new StringBuilder();

        int i = 1;
        for (String key : dialog.keySet()) {
            dialogChoices.append(i).append(". ").append(key).append("\n");
            i++;
        }
        return dialogChoices.toString();
    }

    public void addItemDialog(String item, String response) {
        itemDialog.put(item, response);
    }

    public String getItemDialog(String item) {
        for (String key : itemDialog.keySet()) {
            if (key.equals(item)) {
                return itemDialog.get(key);
            }
        }
        return noItemDialog;
    }

    public void changeNoItemDialog(String tempDialog) {
        noItemDialog = tempDialog;
    }
}
