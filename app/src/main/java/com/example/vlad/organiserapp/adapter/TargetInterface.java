package com.example.vlad.organiserapp.adapter;

import com.example.vlad.organiserapp.CustomEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Adapter Design Pattern
public interface TargetInterface {

    public void adapterCreateAndWriteToXml(CustomEvent customEvent);
    public void adapterModifyXml (CustomEvent customEvent);
    public void adapterAddEventXml(CustomEvent customEvent);
    public void adapterDeleteEvent (int id);
    public CustomEvent adapterGetEventById (int id);
    public ArrayList<CustomEvent> adapterGetCustomEvents();
    public boolean adapterCheckIfExists (String fileName);
    public int adapterGetLastEventId();

}
