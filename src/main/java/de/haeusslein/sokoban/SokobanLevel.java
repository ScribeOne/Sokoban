package de.haeusslein.sokoban;

import de.haeusslein.sokoban.model.Difficulty;
import de.haeusslein.sokoban.util.LevelDataException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.LinkedList;

public class SokobanLevel {
    private String levelName = "";
    private LinkedList<String> authors = new LinkedList<>();
    private int width = 0;
    private int height = 0;
    private char[][] levelData;
    private Difficulty difficulty;

    public SokobanLevel(String filename) {
        Document doc = loadFile(filename);
        validate(filename);
        try {
            parseDocument(doc);
        } catch (LevelDataException e) {
            e.printStackTrace();
        }
    }

    private void validate(String filename) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source xmlFile = new StreamSource(getClass().getResourceAsStream("/" + filename));

        try {
            Schema schema = schemaFactory.newSchema(new File(getClass().getResource("/sokoban.xsd").getFile()));
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
        } catch (SAXException e) {
            System.out.println(filename + " is NOT valid reason:" + e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Document loadFile(String filename) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(getClass().getResourceAsStream("/" + filename));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }


    private void parseDocument(Document document) throws LevelDataException {
        document.getDocumentElement().normalize();

        NodeList authorNodes = document.getElementsByTagName("Author");
        for (int i = 0; i < authorNodes.getLength(); i++) {
            Node authorNode = authorNodes.item(i);
            if (authorNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) authorNode;
                authors.add(element.getTextContent());
            }
        }

        NodeList nameList = document.getElementsByTagName("LevelName");
        if (nameList.getLength() == 0) {
            levelName = "unknown Name";
        } else {
            levelName = nameList.item(0).getTextContent();
        }

        NodeList difficultyNodes = document.getElementsByTagName("Difficulty");
        if (difficultyNodes.getLength() == 0) {
            difficulty = Difficulty.NONE;
        } else {
            difficulty = Difficulty.valueOf(difficultyNodes.item(0).getTextContent());
        }

        Node levelDataNode = document.getElementsByTagName("LevelData").item(0);
        width = Integer.parseInt(levelDataNode.getAttributes().getNamedItem("width").getTextContent());
        height = Integer.parseInt(levelDataNode.getAttributes().getNamedItem("height").getTextContent());

        levelData = new char[height][width];

        String lines[] = levelDataNode.getTextContent().trim().split("\\r?\\n");

        if (lines.length != height) {
            throw new LevelDataException("Height Error");
        }

        for (int i = 0; i < lines.length; i++) {
            char[] line = lines[i].trim().toCharArray();

            if (line.length != width) {
                throw new LevelDataException("Width Error");
            }

            for (int j = 0; j < line.length; j++) {
                levelData[i][j] = line[j];
            }

        }


    }



    /*
    public SokobanLevel(String filename) {
        try (InputStream input = getClass().getResourceAsStream("/" + filename)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(input);

            String currentElement = "";
            int lineCounter = 0;

            while (parser.hasNext()) {
                // System.out.println("Event: " + parser.getEventType());
                switch (parser.getEventType()) {
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Start Document");
                        System.out.println("Version: " + parser.getVersion());
                        System.out.println("Encoding: " + parser.getEncoding());
                        break;

                    case XMLStreamConstants.END_DOCUMENT:
                        System.out.println("End Document");
                        parser.close();
                        break;

                    case XMLStreamConstants.NAMESPACE:
                        System.out.println("Namespace: " + parser.getNamespaceURI());
                        break;

                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("Start Element: " + parser.getLocalName());
                        currentElement = parser.getLocalName();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            if (parser.getAttributeLocalName(i).toLowerCase().equals("width")) {
                                width = Integer.parseInt(parser.getAttributeValue(i));
                            } else if (parser.getAttributeLocalName(i).toLowerCase().equals("height")) {
                                height = Integer.parseInt(parser.getAttributeValue(i));
                            }
                            //System.out.printf("     Attribute %s with Value %s \n", parser.getAttributeLocalName(i), parser.getAttributeValue(i));
                        }
                        if (currentElement.toLowerCase().equals("leveldata")) {
                            levelData = new char[height][width];
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                            System.out.printf("     Characters: %s \n", parser.getText());
                            currentElement = currentElement.toLowerCase();
                            switch (currentElement) {
                                case "author":
                                    authors.add(parser.getText());
                                    break;
                                case "levelname":
                                    levelName = parser.getText();
                                    break;
                                case "leveldata":
                                    char[] line = parser.getText().toCharArray();
                                    for (int i = 0; i < (height - 1); i++) {
                                        System.out.println("adding" + line[i] +"! linecounter: " + lineCounter + " i: " + i);
                                        levelData[lineCounter][i] = line[i];
                                    }
                                    lineCounter++;
                                    break;
                                default:
                                    break;
                        }

                    default:
                        break;
                }
                parser.next();
            }

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    */

    public void printData() {
        System.out.println("Authors: " + authors);
        System.out.println("Level name: " + levelName);
        System.out.println("width:" + width);
        System.out.println("height: " + height);
        System.out.println("Difficulty:" + difficulty.toString());
        for (char[] line : levelData) {
            System.out.println(line);
        }
    }


    public String getLevelName() {
        return levelName;
    }

    public LinkedList<String> getAuthors() {
        return authors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getLevelData() {
        return levelData;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
