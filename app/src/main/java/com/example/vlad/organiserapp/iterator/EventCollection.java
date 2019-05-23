package com.example.vlad.organiserapp.iterator;

import com.example.vlad.organiserapp.nullobject.Event;

public interface EventCollection {

    public void addChannel(Event e);

    public void removeChannel(Event e);

    public EventIterator iterator();

}
