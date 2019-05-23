package com.example.vlad.organiserapp.adapter;

import com.example.vlad.organiserapp.nullobject.CustomEvent;
import com.example.vlad.organiserapp.CustomEventXmlParser;
import com.example.vlad.organiserapp.nullobject.Event;
import com.example.vlad.organiserapp.strategy.StrategyInterface;

import java.util.ArrayList;


// Singleton Design Pattern
// Adapter Design Pattern
public class CustomEventXmlParserAdapter implements TargetInterface {

    private CustomEventXmlParser customEventXmlParser = CustomEventXmlParser.getInstance();

    private CustomEventXmlParserAdapter(){}

    private static class CustomEventXmlParserAdapterHelper {
        private static final CustomEventXmlParserAdapter INSTANCE = new CustomEventXmlParserAdapter();
    }

    public static CustomEventXmlParserAdapter getInstance() {
        return CustomEventXmlParserAdapterHelper.INSTANCE;
    }

    @Override
    public void adapterCreateAndWriteToXml(Event customEvent) {
        customEventXmlParser.createAndWriteToXml(customEvent);
    }

    @Override
    public void adapterModifyXml(Event customEvent) {
        customEventXmlParser.modifyXml(customEvent);
    }

    @Override
    public void adapterAddEventXml(Event customEvent, StrategyInterface strategyInterface) {
        //customEventXmlParser.addEventXml(customEvent);
        strategyInterface.save(customEvent);
    }

    @Override
    public void adapterDeleteEvent(int id) {
        customEventXmlParser.deleteEvent(id);
    }

    @Override
    public CustomEvent adapterGetEventById(int id) {
        return customEventXmlParser.getEventById(id);
    }

    @Override
    public ArrayList<CustomEvent> adapterGetCustomEvents() {
        return customEventXmlParser.getcustomEvents();
    }

    @Override
    public boolean adapterCheckIfExists(String fileName) {
        return customEventXmlParser.checkIfExists(fileName);
    }

    @Override
    public int adapterGetLastEventId() {
        return customEventXmlParser.getLastEventId();
    }
}
