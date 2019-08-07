package de.haeusslein.sokoban;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class SokobanLevel {
    private String levelName = "";
    private LinkedList<String> authors = new LinkedList<>();
    private int width = 0;
    private int height = 0;
    private char[][] levelData;

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
                                        System.out.println("adding! linecounter: " + lineCounter + " i: " + i);
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

    public void printData() {
        System.out.println("Authors: " + authors);
        System.out.println("Level name: " + levelName);
        System.out.println("width:" + width);
        System.out.println("height: " + height);
    }


}
