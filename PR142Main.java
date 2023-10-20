import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;


public class PR142Main {
  static Scanner in = new Scanner(System.in); // System.in és global


  // Main
  public static void main(String[] args) throws InterruptedException, IOException {
    boolean running = true;
    while (running) {
      String menu = "\nEscull una opció:";
      menu = menu + "\n 0) Llistar id de cursos";
      menu = menu + "\n 1) Llistar tutors";
      menu = menu + "\n 2) Llistar total d'alumnes";
      menu = menu + "\n 3) Mostrar ids i titols d'un modul";
      menu = menu + "\n 4) Llistar alumnes d'un curs";
      menu = menu + "\n 5) Afegir alumne a un curs";
      menu = menu + "\n 6) Eliminar alumne d'un curs";
      menu = menu + "\n 100) Sortir";
      System.out.println(menu);


      int opcio = Integer.valueOf(llegirLinia("Opció: "));
      try {
        switch (opcio) {
          case 0: getCursId(); break;
          case 1: getTutors(); break;
          case 2: getTotalAlumnes(); break;
          case 3: getIdsTitolsById(); break;
          case 4: llistarAlumnesPerCurs(); break;
          case 5: addAlumne(); break;
          case 6: ; break;
          case 100: running = false; break;
          default: break;
        }
      } catch (Exception e) {
          System.out.println(e);
      }
    }
    in.close();
  }


  static public String llegirLinia (String text) {
    System.out.print(text);
    return in.nextLine();
  }


  static public void getCursId () {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse("./cursos.xml");
        doc.getDocumentElement().normalize();

        NodeList listCursos = doc.getElementsByTagName("curs");
        System.out.println("\nIds de cursos: ");
        for (int i = 0; i < listCursos.getLength(); i++) {
            Node nodeCurs = listCursos.item(i);
            if (nodeCurs.getNodeType() == Node.ELEMENT_NODE) {
                Element elm = (Element) nodeCurs;
                String id = elm.getAttribute("id");
                System.out.println(id);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }


    static public void getTutors () {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            doc.getDocumentElement().normalize();

            NodeList listCursos = doc.getElementsByTagName("curs");
            System.out.println("\nTutors: ");
            for (int i = 0; i < listCursos.getLength(); i++) {
                Node nodeCurs = listCursos.item(i);
                if (nodeCurs.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) nodeCurs;
                    NodeList nodeListTutor = elm.getElementsByTagName("tutor");
                    String tutor = nodeListTutor.item(0).getTextContent();
                    System.out.println(tutor);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }


    static public void getTotalAlumnes () {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            doc.getDocumentElement().normalize();
            NodeList listCursos = doc.getElementsByTagName("curs");
            int totalAlumnes = 0;
            for (int i = 0; i < listCursos.getLength(); i++) {
                Node nodeCurs = listCursos.item(i);
                if (nodeCurs.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) nodeCurs;
                    String id = elm.getAttribute("id");
                    NodeList listAlumnes = elm.getElementsByTagName("alumnes");
                    Node nodeAlumnes = listAlumnes.item(0);
                    Element elm2 = (Element) nodeAlumnes;
                    NodeList listAlumne = elm2.getElementsByTagName("alumne");
                    totalAlumnes = listAlumne.getLength();
                    System.out.println("Total d'alumnes de " + id + ": " + totalAlumnes);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }


    static public String seleccionarCurs() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            doc.getDocumentElement().normalize();
            NodeList listCursos = doc.getElementsByTagName("curs");
            ArrayList<String> idCursos = new ArrayList<String>();
            for (int i = 0; i < listCursos.getLength(); i++) {
                Node nodeCurs = listCursos.item(i);
                if (nodeCurs.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) nodeCurs;
                    String id = elm.getAttribute("id");
                    idCursos.add(id);
                }
            }

            boolean running = true;
            String idSeleccionada = ""; 
            while (running) {
                String menu = "\nEscull el curs que vols observar:";
                menu = menu + "\n 0) " + idCursos.get(0);
                menu = menu + "\n 1) " + idCursos.get(1);
                menu = menu + "\n 100) Sortir";
                System.out.println(menu);

                int opcio = Integer.valueOf(llegirLinia("Opció: "));
                try {
                    switch (opcio) {
                    case 0: idSeleccionada = idCursos.get(0); running = false; break;
                    case 1: idSeleccionada = idCursos.get(1); running = false; break;
                    case 100: running = false; break;
                    default: break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return idSeleccionada;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static public void getIdsTitolsById() {
    
        try {
            String idSeleccionada = seleccionarCurs();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + idSeleccionada + "']"; 
            NodeList listCursXpath = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            Node nodeCursXpath = listCursXpath.item(0);
            Element curs = (Element) nodeCursXpath;
            NodeList listModuls = curs.getElementsByTagName("moduls");
            Node nodeModuls = listModuls.item(0);
            Element moduls = (Element) nodeModuls;
            NodeList listModul = moduls.getElementsByTagName("modul");
            for (int i = 0; i < listModul.getLength(); i++) {
                Node nodeModul = listModul.item(i);
                Element modul = (Element) nodeModul;
                String id = modul.getAttribute("id");
                NodeList listTitol = modul.getElementsByTagName("titol");
                String titol = listTitol.item(0).getTextContent();
                System.out.println("Modul " + id + ": " + titol);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    static public void llistarAlumnesPerCurs() {
        try {
            String idSeleccionada = seleccionarCurs();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + idSeleccionada + "']"; 
            NodeList listCursXpath = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            Node nodeCursXpath = listCursXpath.item(0);
            Element curs = (Element) nodeCursXpath;
            NodeList listAlumnes = curs.getElementsByTagName("alumnes");
            Node nodeAlumnes = listAlumnes.item(0);
            Element alumnes = (Element) nodeAlumnes;
            NodeList listAlumne = alumnes.getElementsByTagName("alumne");
            System.out.println("Alumnes de " + idSeleccionada + ":");
            for (int i = 0; i < listAlumne.getLength(); i++) {
                System.out.println(listAlumne.item(i).getTextContent());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    static public void addAlumne() {
        try {
            String idSeleccionada = seleccionarCurs();

            Scanner scan;
            boolean nameCorrect = true, surnameCorrect = true;
            String newName = "", newSurname = "";
            while (nameCorrect && surnameCorrect) {
                scan = new Scanner(System.in);
                System.out.println("Introdueix el nom del nou alumne:");
                if (scan.hasNextLine()) {
                    newName = scan.nextLine();
                    nameCorrect = false;
                } else {
                    System.out.println("El valor introduit es incorrecte.");
                }

                scan = new Scanner(System.in);
                System.out.println("Introdueix el cognom del nou alumne:");
                if (scan.hasNextLine()) {
                    newSurname = scan.nextLine();
                    surnameCorrect = false;
                } else {
                    System.out.println("El valor introduit es incorrecte.");
                }
            }

            String newAlumneName = newSurname.toUpperCase() + ", " + newName;
            System.out.println(newAlumneName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + idSeleccionada + "']"; 
            NodeList listCursXpath = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            Node nodeCursXpath = listCursXpath.item(0);
            Element curs = (Element) nodeCursXpath;
            NodeList listAlumnes = curs.getElementsByTagName("alumnes");
            Node nodeAlumnes = listAlumnes.item(0);
            Element alumnes = (Element) nodeAlumnes;
            
            Element alumne = doc.createElement("alumne");
            Text alumneNom = doc.createTextNode(newAlumneName);
            alumne.appendChild(alumneNom);
            alumnes.appendChild(alumne);
            write("./cursos.xml", doc);
        } catch(Exception e) {
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