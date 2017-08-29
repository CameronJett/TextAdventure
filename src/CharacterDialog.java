import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterDialog {
    private Map<String, String> dialog;
    private Map<String, Character> linkHiddenDialog;
    private Map<Character, Map<String, String>> hiddenDialog;

    private Map<String, String> itemDialog;
    private String noItemDialog;

    public CharacterDialog() {
        dialog = new LinkedHashMap<>();
        linkHiddenDialog = new HashMap<>();
        hiddenDialog = new HashMap<>();

        itemDialog = new HashMap<>();
        noItemDialog = Const.DEFAULT_NO_ITEM_DIALOG;
    }

    public void add(String option, String response, Character dialogLink) {
        dialog.put(option, response);
        if (!dialogLink.equals(Const.NULL_CHAR)) {
            linkHiddenDialog.put(option, dialogLink);
        }
    }

    public void addAll(Map<String, String> dialogToAdd) { dialog.putAll(dialogToAdd); }

    public void addHidden(Character hiddenLink, String option, String response) {
        Map<String, String> tempDialog = new HashMap<>();
        tempDialog.put(option, response);
        hiddenDialog.put(hiddenLink, tempDialog);
    }

    public boolean hasDialog(String option) { return dialog.containsKey(option); }

    public String getDialog(String option) {
        if (linkHiddenDialog.containsKey(option)) {
            Character link = linkHiddenDialog.get(option);
            if (hiddenDialog.containsKey(link)) {
                dialog.putAll(hiddenDialog.get(link));
            }
        }
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

    public void addItemDialog(String item, String response) { itemDialog.put(item, response); }

    public String getItemDialog(String item) {
        for (String key : itemDialog.keySet()) {
            if (key.equals(item)) {
                return itemDialog.get(key);
            }
        }
        return noItemDialog;
    }

    public void changeNoItemDialog(String tempDialog) { noItemDialog = tempDialog; }
}
