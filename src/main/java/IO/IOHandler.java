package IO;

import controller.ViewController;
import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



/**
 * Kezeli az exportokat és importokat XML-ből.
 *
 */
public class IOHandler {

    /**
     * Példányosított Logger osztály.
     */
    private static Logger logger = LoggerFactory.getLogger(IOHandler.class);

    /**
     * KiÍrja xml-be a feladatok listáját, a kiválasztott helyre.
     * @param outputPath a menteni kivánt hely elérési útja
     * @param tasks feladatok listája
     */
    public static void XmlWriter(File outputPath, List<Task> tasks) {

        try {
            DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            //fa felepitese
            Element root = doc.createElement("tasks");
            doc.appendChild(root);

            for (Task currentTask : tasks) {

                Element task = doc.createElement("task");
                root.appendChild(task);

                Element taskDesc = doc.createElement("taskDesc");
                taskDesc.appendChild(doc.createTextNode(currentTask.getTaskDesc()));
                task.appendChild(taskDesc);

                Element priority = doc.createElement("priority");
                priority.appendChild(doc.createTextNode(currentTask.getPriority()));
                task.appendChild(priority);

                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(String.valueOf(currentTask.getTaskDate())));
                task.appendChild(date);

                Element done = doc.createElement("done");
                done.appendChild(doc.createTextNode(String.valueOf(currentTask.isDone())));
                task.appendChild(done);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath.toURI()));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            logger.error("Nem sikerult letrehozni a DocumentBuildert: " + e);
        } catch (TransformerConfigurationException e) {
            logger.error("Nem sikerult a transzformalas: " + e);
        } catch (TransformerException e) {
            logger.error("Nem sikerult menteni a felepitett fat: " + e);
        }
    }

    /**
     * A kiválasztott xml fájlt beolvassa es a benne lévő adatokat elmenti egy listába.
     * @param imputXML beérkező xml fájl
     * @return olyan listával tér vissza, amelyben a beolvasott feladatok vannak
     */
    public static List<Task> XmlReader(File imputXML){

        List<Task> tasksDB =new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File imput = null;
            imput = imputXML;

            Document document = db.parse(imput);

            NodeList nodeList = document.getElementsByTagName("task");

            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String taskDescDB = element.getElementsByTagName("taskDesc").item(0).getTextContent();
                    String priorityDB = element.getElementsByTagName("priority").item(0).getTextContent();
                    LocalDate localDateDB = LocalDate.parse(element.getElementsByTagName("date").item(0).getTextContent());
                    Boolean doneDB = Boolean.parseBoolean(element.getElementsByTagName("done").item(0).getTextContent());
                    tasksDB.add(new Task(taskDescDB,priorityDB, localDateDB, doneDB));
                }
            }

        } catch (ParserConfigurationException e) {
            logger.error("Nem sikerult letrehozni a DocumentBuildert: " + e);
        } catch (SAXException e) {
            logger.error("Elemzesi hiba keletkezett a parse-nal: " +e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IO error a parse-nal: " +e);
        }
        return tasksDB;
    }
}
