package com.example.vlad.organiserapp.flyweight;


import com.example.vlad.organiserapp.nullobject.CustomEvent;
import com.example.vlad.organiserapp.adapter.CustomEventXmlParserAdapter;
import com.example.vlad.organiserapp.adapter.TargetInterface;


import java.util.HashMap;


// Flyweight Design Pattern
// Factory Design Pattern
public class CustomEventFactory {

    private static TargetInterface targetInterface = CustomEventXmlParserAdapter.getInstance();

    private static HashMap< Integer, CustomEvent> customEventHashMap = new HashMap<>();

    public static CustomEvent getCustomEvent(Integer id){
        CustomEvent customEvent = customEventHashMap.get(id);

        if (customEvent == null) {
            customEvent = targetInterface.adapterGetEventById(id);
            customEventHashMap.put(id,customEvent);
            }

        return customEvent;
    }



}
