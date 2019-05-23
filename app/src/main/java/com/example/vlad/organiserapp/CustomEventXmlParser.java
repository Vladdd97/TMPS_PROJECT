package com.example.vlad.organiserapp;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

// Singleton Design Pattern
public class CustomEventXmlParser {

    public static String fileName = "events.xml";

    private CustomEventXmlParser() {
    }

    private static class CustomEventXmlParserHelper {
        private static final CustomEventXmlParser INSTANCE = new CustomEventXmlParser();
    }

    public static CustomEventXmlParser getInstance() {
        return CustomEventXmlParserHelper.INSTANCE;
    }

    public void createAndWriteToXml(CustomEvent customEvent) {

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("OrganaizerAppEvents");
            doc.appendChild(rootElement);

            // event element
            Element event = doc.createElement("event");
            rootElement.appendChild(event);

            // setting eventId attribute to event element
            Attr attr = doc.createAttribute("eventId");
            attr.setValue("1");
            event.setAttributeNode(attr);


            // id element
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(Integer.toString(customEvent.getId())));
            event.appendChild(id);

            // title element
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(customEvent.getTitle()));
            event.appendChild(title);

            // description element
            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode(customEvent.getDescription()));
            event.appendChild(description);

            // isAlarmSet element
            Element isAlarmSet = doc.createElement("isAlarmSet");
            isAlarmSet.appendChild(doc.createTextNode(Integer.toString(customEvent.getIsAlarmSet())));
            event.appendChild(isAlarmSet);


            // date element , will be stored in a Long format
            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(Long.toString(customEvent.getDate().getTime())));
            event.appendChild(date);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // will modify an event base on modId
    public void modifyXml(CustomEvent customEvent) {

        int modId = customEvent.getId();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // find all event tags
            NodeList eventsList = doc.getElementsByTagName("event");

            // loop all events
            for (int i = 0; i < eventsList.getLength(); i++) {
                Node event = eventsList.item(i);
                if (event.getNodeType() == Node.ELEMENT_NODE) {

                    Element eventElement = (Element) event;
                    // find eventId
                    int eventId = Integer.parseInt(eventElement.getAttribute("eventId"));

                    // check if it is the wanted event
                    if (modId == eventId) {

                        NodeList eventChilds = event.getChildNodes();
                        // loop all event's tag
                        for (int j = 0; j < eventChilds.getLength(); j++) {
                            Node node = eventChilds.item(j);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) node;
                                // mod id element
                                //if ("id".equals(eElement.getNodeName()))
                                //eElement.setTextContent(Integer.toString(customEvent.getId()));
                                // mod title element
                                if ("title".equals(eElement.getNodeName()))
                                    eElement.setTextContent(customEvent.getTitle());
                                // mod description element
                                if ("description".equals(eElement.getNodeName()))
                                    eElement.setTextContent(customEvent.getDescription());
                                // mod isAlarmSet
                                if ("isAlarmSet".equals(eElement.getNodeName()))
                                    eElement.setTextContent(Integer.toString(customEvent.getIsAlarmSet()));
                                // mod date element
                                if ("date".equals(eElement.getNodeName()))
                                    eElement.setTextContent(Long.toString(customEvent.getDate().getTime()));
                            }
                        }
                    }
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addEventXml(CustomEvent customEvent) {


        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // root node
            Node rootNoode = doc.getFirstChild();
            Element rootElement = (Element) rootNoode;


            // event element
            Element event = doc.createElement("event");
            rootElement.appendChild(event);

            // setting eventId attribute to event element
            Attr attr = doc.createAttribute("eventId");
            attr.setValue(Integer.toString(customEvent.getId()));
            event.setAttributeNode(attr);


            // id element
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(Integer.toString(customEvent.getId())));
            event.appendChild(id);

            // title element
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(customEvent.getTitle()));
            event.appendChild(title);

            // description element
            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode(customEvent.getDescription()));
            event.appendChild(description);

            // isAlarmSet element
            Element isAlarmSet = doc.createElement("isAlarmSet");
            isAlarmSet.appendChild(doc.createTextNode(Integer.toString(customEvent.getIsAlarmSet())));
            event.appendChild(isAlarmSet);

            // date element
            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(Long.toString(customEvent.getDate().getTime())));
            event.appendChild(date);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteEvent(int deleteId) {


        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // root element
            Node rootNoode = doc.getFirstChild();

            // find all event tags
            NodeList eventsList = doc.getElementsByTagName("event");

            // loop all events
            for (int i = 0; i < eventsList.getLength(); i++) {
                Node event = eventsList.item(i);
                if (event.getNodeType() == Node.ELEMENT_NODE) {

                    Element eventElement = (Element) event;
                    // find eventId
                    int eventId = Integer.parseInt(eventElement.getAttribute("eventId"));

                    // check if it is the wanted event
                    if (deleteId == eventId) {
                        rootNoode.removeChild(event);
                    }
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // get Event by getId
    public CustomEvent getEventById(int getId) {

        CustomEvent customEvent = new CustomEvent.
                CustomEventBuilder()
                .setId(0)
                .setTitle(null)
                .setDescription(null)
                .setIsAlarmSet(0)
                .setDate(null)
                .build();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // find all event tags
            NodeList eventsList = doc.getElementsByTagName("event");

            // loop all events
            for (int i = 0; i < eventsList.getLength(); i++) {
                Node event = eventsList.item(i);
                if (event.getNodeType() == Node.ELEMENT_NODE) {

                    Element eventElement = (Element) event;
                    // find eventId
                    int eventId = Integer.parseInt(eventElement.getAttribute("eventId"));

                    // check if it is the wanted event
                    if (getId == eventId) {

                        NodeList eventChilds = event.getChildNodes();
                        // loop all event's tag
                        for (int j = 0; j < eventChilds.getLength(); j++) {
                            Node node = eventChilds.item(j);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) node;
                                // set id element
                                if ("id".equals(eElement.getNodeName()))
                                    customEvent.setId(Integer.parseInt(eElement.getTextContent()));
                                // set title element
                                if ("title".equals(eElement.getNodeName()))
                                    customEvent.setTitle(eElement.getTextContent());
                                // set description element
                                if ("description".equals(eElement.getNodeName()))
                                    customEvent.setDescription(eElement.getTextContent());
                                // set isAlarmSet element
                                if ("isAlarmSet".equals(eElement.getNodeName()))
                                    customEvent.setIsAlarmSet(Integer.parseInt(eElement.getTextContent()));
                                // set date element
                                if ("date".equals(eElement.getNodeName()))
                                    customEvent.setDate(new Date(Long.parseLong(eElement.getTextContent())));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return customEvent;
    }


    public ArrayList<CustomEvent> getcustomEvents() {

        ArrayList<CustomEvent> customEventList = new ArrayList<>();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // find all event tags
            NodeList eventsList = doc.getElementsByTagName("event");

            // loop all events
            for (int i = 0; i < eventsList.getLength(); i++) {
                Node event = eventsList.item(i);
                if (event.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList eventChilds = event.getChildNodes();
                    CustomEvent customEventtt = new CustomEvent.
                            CustomEventBuilder()
                            .setId(0)
                            .setTitle(null)
                            .setDescription(null)
                            .setIsAlarmSet(0)
                            .setDate(null)
                            .build();

                    customEventList.add(customEventtt);
                    // loop all event's tag
                    for (int j = 0; j < eventChilds.getLength(); j++) {
                        Node node = eventChilds.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) node;
                            // mod id element
                            if ("id".equals(eElement.getNodeName()))
                                customEventtt.setId(Integer.parseInt(eElement.getTextContent()));
                            // mod title element
                            if ("title".equals(eElement.getNodeName()))
                                customEventtt.setTitle(eElement.getTextContent());
                            // mod description element
                            if ("description".equals(eElement.getNodeName()))
                                customEventtt.setDescription(eElement.getTextContent());
                            // mod isAlarmSet element
                            if ("isAlarmSet".equals(eElement.getNodeName()))
                                customEventtt.setIsAlarmSet(Integer.parseInt(eElement.getTextContent()));
                            // mod date element
                            if ("date".equals(eElement.getNodeName()))
                                customEventtt.setDate(new Date(Long.parseLong(eElement.getTextContent())));
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customEventList;
    }


    public boolean checkIfExists(String fileName) {
        File file = new File(fileName);
        boolean exists = file.exists();
        return exists;
    }

    // get eventId of the last event
    public int getLastEventId() {
        int lastEventId = 0;
        if (!checkIfExists(CustomEventXmlParser.fileName)) {
            return lastEventId;
        }


        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // find all event tags
            NodeList eventsList = doc.getElementsByTagName("event");

            // get the last event
            Node lastEventNode = eventsList.item(eventsList.getLength() - 1);
            Element lastEventElement = (Element) lastEventNode;
            // get the eventId of the last event
            lastEventId = Integer.parseInt(lastEventElement.getAttribute("eventId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastEventId;
    }

}