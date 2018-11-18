package com.github.bloodriot.event;

public final class EventData {
    private final Object data;
    private EventTypes eventType;

    public EventData(final EventTypes eventType, final Object data) {
        this.data = data;
        this.eventType = eventType;
    }

    public EventTypes getEventType() {
        return this.eventType;
    }

    public void setEventType(final EventTypes eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return this.data;
    }
}
