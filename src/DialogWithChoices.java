public class DialogWithChoices {
    private String option;
    private Dialog dialog;
    private DialogWithChoices replacementDialogWithChoices; //replaces this dialog after the dialog is read
    private DialogWithChoices hiddenDialogWithChoices; //adds this dialog after the dialog is read

    DialogWithChoices() {
    }

    DialogWithChoices(String dialogOption, Dialog dialogText) {
        this();
        option = dialogOption;
        dialog = dialogText;
    }

    public void addDialogPair(String dialogOption, String dialogText) {
        addDialogPair(dialogOption, new Dialog(dialogText));
    }

    public void addDialogPair(String dialogOption, Dialog dialogText) {
        option = dialogOption;
        dialog = dialogText;
    }

    public String getDialogOption() {
        return option;
    }

    public Dialog readDialog(CharacterDialog cDialog) {
        Dialog returnDialog = dialog;
        replaceDialog();
        setHiddenDialog(cDialog);
        return returnDialog;
    }

    public void addReplacementDialog(DialogWithChoices replacementDialog) {
        replacementDialogWithChoices = new DialogWithChoices(replacementDialog.getDialogOption(), replacementDialog.getDialog());
    }

    public void addHiddenDialog(DialogWithChoices hiddenDialog) {
        hiddenDialogWithChoices = new DialogWithChoices();
        hiddenDialogWithChoices = hiddenDialog;
    }

    private DialogWithChoices getReplacementDialogWithChoices() {
        return replacementDialogWithChoices;
    }

    private void replaceDialog() {
        if (replacementDialogWithChoices != null) {
            option = replacementDialogWithChoices.getDialogOption();
            dialog = replacementDialogWithChoices.getDialog();
            replacementDialogWithChoices = replacementDialogWithChoices.getReplacementDialogWithChoices();
        }
    }

    private void setHiddenDialog(CharacterDialog cDialog) {
        if (hiddenDialogWithChoices != null) {
            cDialog.add(hiddenDialogWithChoices);
        }
    }

    private Dialog getDialog() {
        return dialog;
    }
}
