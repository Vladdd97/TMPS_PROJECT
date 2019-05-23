package com.example.vlad.organiserapp.iterator;

import com.example.vlad.organiserapp.nullobject.Event;

import java.util.ArrayList;
import java.util.List;

public class EventCollectionImpl implements EventCollection {

    private List<Event> eventList;

    public EventCollectionImpl() {
        eventList = new ArrayList<>();
    }

    @Override
    public void addChannel(Event e) {
        this.eventList.add(e);
    }

    @Override
    public void removeChannel(Event e) {
        this.eventList.remove(e);
    }

    @Override
    public EventIterator iterator() {
        return new EventIteratorImpl(this.eventList);
    }

    private class EventIteratorImpl implements EventIterator {

        private List<Event> events;
        private int position;

        public EventIteratorImpl(List<Event> events) {
            this.events = events;
        }

        @Override
        public boolean hasNext() {
            if (position < events.size())
                return true;
            else
                return false;
        }

        @Override
        public Event next() {
            Event event = eventList.get(position);
            position++;
            return event;
        }
    }
}
