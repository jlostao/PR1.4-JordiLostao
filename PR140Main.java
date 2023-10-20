import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class PR140Main {
    public static void main(String args[]) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./persones.xml");
            doc.getDocumentElement().normalize();

            NodeList listPersones = doc.getElementsByTagName("persona");

            for (int i = 0; i < listPersones.getLength(); i++) {
                Node nodePersona = listPersones.item(i);
                if (nodePersona.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) nodePersona;

                    NodeList nodeListNom = elm.getElementsByTagName("nom");
                    String nom = nodeListNom.item(0).getTextContent();
                    
                    NodeList nodeListCognom = elm.getElementsByTagName("cognom");
                    String cognom = nodeListCognom.item(0).getTextContent();

                    NodeList nodeListEdat = elm.getElementsByTagName("edat");
                    String edat = nodeListEdat.item(0).getTextContent();

                    NodeList nodeListCiutat = elm.getElementsByTagName("ciutat");
                    String ciutat = nodeListCiutat.item(0).getTextContent();
                    
                    
                    System.out.println("\nNom: " + nom + "  Cognom: " + cognom + "  Edat: " + edat + "  Ciutat: " + ciutat);

                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        } 

    }
}

