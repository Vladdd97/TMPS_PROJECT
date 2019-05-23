package com.example.vlad.organiserapp.strategy;

import android.util.Log;

import com.example.vlad.organiserapp.CustomEventXmlParser;
import com.example.vlad.organiserapp.nullobject.Event;

public class StrategyImpl implements StrategyInterface {
    private CustomEventXmlParser customEventXmlParser = CustomEventXmlParser.getInstance();

    @Override
    public void save(Event event) {
        Log.d("AddEventActivity","Was added an event : "+event.toString());
        customEventXmlParser.addEventXml(event);
    }
}
