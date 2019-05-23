package com.example.vlad.organiserapp.adapter;

import com.example.vlad.organiserapp.nullobject.CustomEvent;
import com.example.vlad.organiserapp.nullobject.Event;
import com.example.vlad.organiserapp.strategy.StrategyInterface;

import java.util.ArrayList;

// Adapter Design Pattern
public interface TargetInterface {

    public void adapterCreateAndWriteToXml(Event customEvent);
    public void adapterModifyXml (Event customEvent);
    public void adapterAddEventXml(Event customEvent, StrategyInterface strategyInterface);
    public void adapterDeleteEvent (int id);
    public CustomEvent adapterGetEventById (int id);
    public ArrayList<CustomEvent> adapterGetCustomEvents();
    public boolean adapterCheckIfExists (String fileName);
    public int adapterGetLastEventId();

}
