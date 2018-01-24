import java.util.*;

public class CharacterDialog {
    private List<DialogWithChoices> dialog;

    private Map<String, Dialog> itemDialog;
    private Dialog noItemDialog;

    public CharacterDialog() {
        dialog = new ArrayList<>();

        itemDialog = new HashMap<>();
        noItemDialog = new Dialog(Const.DEFAULT_NO_ITEM_DIALOG);
    }

    public void add(String option, String response) {
        this.add(option, new Dialog(response));
    }

    public void add(String option, Dialog response) {
        this.add(new DialogWithChoices(option, response));
    }

    public void add(DialogWithChoices dialogContainer) {
        dialog.add(dialogContainer);
    }

    public void addAll(List<DialogWithChoices> dialogList) {
        dialog.addAll(dialogList);
    }

    public boolean hasDialog(String option) {
        for (DialogWithChoices aDialog : dialog) {
            if (aDialog.getDialogOption().equals(option)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDialog(int i) {
        return dialog.size() >= i - 1 && i > 0;
    }

    public Dialog getDialog(String option) {
        for (DialogWithChoices aDialog : dialog) {
            if (aDialog.getDialogOption().equals(option)) {
                return aDialog.readDialog(this);
            }
        }
        return new Dialog();
    }

    public Dialog getDialog(int option) {
        if (dialog.size() >= option) {
            return dialog.get(option-1).readDialog(this);
        }
        return new Dialog();  //TODO: return meaningful response?
    }

    public Dialog getDialogChoices() {
        StringBuilder dialogLine = new StringBuilder();
        Dialog returnDialog = new Dialog();

        for (int i=0; i < dialog.size(); i++) {
            dialogLine.append(i+1).append(". ").append(dialog.get(i).getDialogOption());
            returnDialog.addLineOfDialog(dialogLine.toString());
            dialogLine.setLength(0);
        }

        return returnDialog;
    }

    public void addItemDialog(String item, Dialog response) { itemDialog.put(item, response); }

    public Dialog getItemDialog(String item) {
        for (String key : itemDialog.keySet()) {
            if (key.equalsIgnoreCase(item)) {
                return itemDialog.get(key);
            }
        }
        return noItemDialog;
    }

    public void changeNoItemDialog(Dialog tempDialog) { noItemDialog = tempDialog; }
}
