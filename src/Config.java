
// using an old config file that i used before but i modified it
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         This class represents a configuration object that can
 *         be used to save and
 *         load game settings. The configuration
 * 
 *         is stored in an XML file named "config.xml". The file
 *         contains two elements:
 *         "name" and "impostor". The "name"
 * 
 *         element is a String that represents the player's
 *         name, and the "impostor"
 *         element is a boolean that indicates
 * 
 *         whether the player is an impostor or not.
 * 
 *         Note: This implementation uses an old config file
 *         that was previously used,
 *         but has been modified to accommodate
 * 
 *         the changes made in the game.
 */

public class Config {
    private static final String FILENAME = "config.xml";
    private String name;
    private boolean impostor;

    public void setImpostor(boolean impostor) {
        this.impostor = impostor;
    }

    public boolean getImpostor() {
        return impostor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Saves the configuration to the XML file.
     */
    public void saveConfig() {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Create root element
            Document doc = db.newDocument();
            Element rootElement = doc.createElement("config");
            doc.appendChild(rootElement);

            // Create name element
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));
            rootElement.appendChild(nameElement);

            Element impostorElement = doc.createElement("impostor");
            impostorElement.appendChild(doc.createTextNode(Boolean.toString(impostor)));
            rootElement.appendChild(impostorElement);

            // Write to XML file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILENAME));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration from the XML file.
     */
    public void loadConfig() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse XML file
            File file = new File(FILENAME);
            if (file.exists()) {

                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();

                // Get name element
                NodeList nameList = doc.getElementsByTagName("name");
                if (nameList.getLength() > 0) {
                    Element nameElement = (Element) nameList.item(0);
                    name = nameElement.getTextContent();
                }
                NodeList impostorList = doc.getElementsByTagName("impostor");
                if (impostorList.getLength() > 0) {
                    Element impostorElement = (Element) impostorList.item(0);
                    impostor = Boolean.parseBoolean(impostorElement.getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}