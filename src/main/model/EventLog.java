package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// A log of all events that has occurred in the open application. Uses the Singleton Design Pattern to ensure that
// there is only one EventLog that the system has global access to the single instance of the EventLog.
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private final Collection<Event> events;

    // MODIFIES: this
    // EFFECTS: used for internal construction
    private EventLog() {
        events = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Gets instance of EventLog and creates it if it doesn't already exist.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    // EFFECTS: return the iterator of the events field
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
