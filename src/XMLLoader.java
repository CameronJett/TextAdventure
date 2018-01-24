import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLLoader implements Load {
    private TextParser parser;

    XMLLoader(TextParser textParser) {
        parser = textParser;
    }

    @Override
    public boolean load(Game game,  String filename) {
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList entityList = doc.getElementsByTagName("entity");

            ArrayList<Node> roomNodes = new ArrayList<>();
            ArrayList<Node> personNodes = new ArrayList<>();
            ArrayList<Node> itemNodes = new ArrayList<>();

            //loop through and create all rooms/people/items
            for (int i=0; i<entityList.getLength(); i++) {
                if (entityList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) entityList.item(i);

                    String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    Dialog description = buildDialog(eElement.getElementsByTagName("description").item(0));

                    switch (type) {
                        case "room":
                            Room room = new Room(name, description);
                            game.addRoom(room);

                            //the first room in the file is the starting room
                            if (game.getCurrentRoom() == null) {
                                game.setCurrentRoom(room);
                            }
                            roomNodes.add(entityList.item(i));
                            break;
                        case "person":
                            Person person = new Person(name, description);
                            game.addPerson(person);
                            personNodes.add(entityList.item(i));
                            break;
                        case "item":
                            Item item = new Item(name, description);
                            game.addItem(item);
                            itemNodes.add(entityList.item(i));
                            break;
                    }
                }
            }

            for (Node roomNode : roomNodes) {  //looping through again and add detail to each room
                if (roomNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) roomNode;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    Room room = game.getRoom(name);

                    NodeList tList = eElement.getElementsByTagName("entrance");
                    for (int j = 0; j < tList.getLength(); j++) {
                        buildEntranceDialog(tList.item(j), room);
                    }

                    tList = eElement.getElementsByTagName("exit");
                    for (int j = 0; j < tList.getLength(); j++) {
                        Room exitRoom = game.getRoom(tList.item(j).getTextContent());
                        room.addExit(exitRoom);
                    }

                    tList = eElement.getElementsByTagName("interest");
                    for (int j = 0; j < tList.getLength(); j++) {
                        buildPointOfInterest(tList.item(j), room);
                    }

                    tList = eElement.getElementsByTagName("person");
                    for (int j = 0; j < tList.getLength(); j++) {
                        Person person = game.getPerson(tList.item(j).getTextContent());
                        room.addPerson(person);
                    }

                    tList = eElement.getElementsByTagName("hold_item");
                    for (int j = 0; j < tList.getLength(); j++) {
                        Item item = game.getItem(tList.item(j).getTextContent());
                        room.addItem(item);
                    }
                }
            }

            for (Node personNode : personNodes) {  //looping through again and add detail to each person
                if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) personNode;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    Person person = game.getPerson(name);

                    NodeList tList = eElement.getChildNodes();
                    for (int j = 0; j < tList.getLength(); j++) {
                        //build dialog
                        if (tList.item(j).getNodeName().equals("dialog")) {
                            person.addDialog(buildDialogWithOptions(tList.item(j)));
                        }
                    }

                    tList = eElement.getElementsByTagName("interest");
                    for (int j = 0; j < tList.getLength(); j++) {
                        buildPointOfInterest(tList.item(j), person);
                    }

                    tList = eElement.getElementsByTagName("entrance");
                    for (int j = 0; j < tList.getLength(); j++) {
                        buildEntranceDialog(tList.item(j), person);
                    }

                    tList = eElement.getElementsByTagName("item_hold");
                    for (int j = 0; j < tList.getLength(); j++) {
                        Item item = game.getItem(tList.item(j).getTextContent());
                        person.addItem(item);
                    }

                    tList = eElement.getElementsByTagName("item_text");
                    String item = "";
                    Dialog dialog = new Dialog();
                    for (int j = 0; j < tList.getLength(); j++) {
                        if (tList.item(j).getNodeName().equals("name")) {
                            item = tList.item(j).getTextContent();
                        } else if (tList.item(j).getNodeName().equals("dialog")) {
                            dialog = buildDialog(tList.item(j));
                        }
                        person.addItemDialog(item, dialog);
                    }

                    tList = eElement.getElementsByTagName("item_no_text");
                    for (int j = 0; j < tList.getLength(); j++) {
                        person.changeNoItemDialog(buildDialog(tList.item(j).getFirstChild()));
                    }
                }
            }

            for (Node itemNode : itemNodes) {  //looping through again and add detail to each item
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) itemNode;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    Item item = game.getItem(name);

                    NodeList tList = eElement.getElementsByTagName("exit");
                    for (int j = 0; j < tList.getLength(); j++) {
                        item.addExitAfterUse(game.getRoom(tList.item(j).getTextContent()));
                    }

                    tList = eElement.getElementsByTagName("use");
                    for (int j = 0; j < tList.getLength(); j++) {
                        item.addUseLocation(game.getRoom(tList.item(j).getTextContent()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private DialogWithChoices buildDialogWithOptions(Node dialogNode) {
        Dialog dialog = buildDialog(dialogNode);
        String option = "";
        DialogWithChoices returnDialog = new DialogWithChoices(option, dialog);

        NodeList childNodes = dialogNode.getChildNodes();
        for (int i=0; i < childNodes.getLength(); i++) {
            switch(childNodes.item(i).getNodeName()) {
                case "option":
                    option = childNodes.item(i).getTextContent();
                    break;
                case "dialog":
                    String xxx = childNodes.item(i).getNodeName();
                    returnDialog.addReplacementDialog(buildDialogWithOptions(childNodes.item(i)));
                    break;
                case "hidden":
                    returnDialog.addHiddenDialog(buildDialogWithOptions(childNodes.item(i)));
                    break;
            }
        }
        returnDialog.addDialogPair(option, dialog);
        return returnDialog;
    }

    private Dialog buildDialog(Node dialogNode) {
        Dialog dialog = new Dialog();

        NodeList childNodes = dialogNode.getChildNodes();
        for (int i=0; i < childNodes.getLength(); i++) {
            switch (childNodes.item(i).getNodeName()) {
                case "text":
                    dialog.addLineOfDialog(childNodes.item(i).getTextContent());
                    break;
            }
        }
        return dialog;
    }

    private void buildPointOfInterest(Node interestNode, Interactable object) {
        NodeList childList = interestNode.getChildNodes();
        String iName = "";
        Dialog iDialog = new Dialog();

        for (int k = 0; k < childList.getLength(); k++) {
            if (childList.item(k).getNodeName().equals("name")) {
                iName = childList.item(k).getTextContent();
            } else if (childList.item(k).getNodeName().equals("dialog")) {
                iDialog = buildDialog(childList.item(k));
            }
        }
        object.addPointOfInterest(iName, iDialog);
        parser.addObject(iName);
    }

    private void buildEntranceDialog(Node entranceNode, Interactable room) {
        room.createIntroductionDialog(buildDialog(entranceNode));
    }
}
