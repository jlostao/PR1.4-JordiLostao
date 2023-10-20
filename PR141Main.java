import java.io.File;
import java.io.IOException;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class PR141Main {
    public static void main(String args[]) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            
            Document doc = db.newDocument();
            Element elmRoot = doc.createElement("biblioteca");
            doc.appendChild(elmRoot);
            Element elmBook = doc.createElement("llibre");
            Attr attrId = doc.createAttribute("id");
            attrId.setValue("001");
            elmBook.setAttributeNode(attrId);

            Element elmTitol = doc.createElement("titol");
            Text nodeTitolText = doc.createTextNode("El viatge dels venturons");
            elmTitol.appendChild(nodeTitolText);
            elmBook.appendChild(elmTitol);

            Element elmAutor = doc.createElement("autor");
            Text nodeAutorText = doc.createTextNode("Joan Pla");
            elmAutor.appendChild(nodeAutorText);
            elmBook.appendChild(elmAutor);

            Element elmAnypublicacio = doc.createElement("anyPublicacio");
            Text nodeAnypublicacioText = doc.createTextNode("1998");
            elmAnypublicacio.appendChild(nodeAnypublicacioText);
            elmBook.appendChild(elmAnypublicacio);
            
            Element elmEditorial = doc.createElement("editorial");
            Text nodeEditorialText = doc.createTextNode("Edicions Mar");
            elmEditorial.appendChild(nodeEditorialText);
            elmBook.appendChild(elmEditorial);
            
            Element elmGenere = doc.createElement("genere");
            Text nodeGenereText = doc.createTextNode("Aventura");
            elmGenere.appendChild(nodeGenereText);
            elmBook.appendChild(elmGenere);
            
            Element elmPagines = doc.createElement("pagines");
            Text nodePaginesText = doc.createTextNode("320");
            elmPagines.appendChild(nodePaginesText);
            elmBook.appendChild(elmPagines);
            
            Element elmDisponible = doc.createElement("disponible");
            Text nodeDisponibleText = doc.createTextNode("true");
            elmDisponible.appendChild(nodeDisponibleText);
            elmBook.appendChild(elmDisponible);

            elmRoot.appendChild(elmBook);
            write("./biblioteca.xml", doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    static public void write (String path, Document doc) throws TransformException, IOException {
        try {
            if (!new File(path).exists()) { new File(path).createNewFile(); }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
}