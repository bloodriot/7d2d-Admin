package com.github.bloodriot.event.horde;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.Event;
import com.github.bloodriot.event.EventData;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class Warning extends Event {

    @Inject
    public Warning(final Configuration configuration, final EventBus eventBus) {
        super(configuration, eventBus);
        setEventType(EventTypes.HORDE_WARNING);
    }

    @Override
    public void onEventTriggered(EventData event) {
        System.out.println(this.toString());
        System.out.println(getConfiguration().getProperty("blood-moon-warning"));
    }

    @Subscribe
    public void listen(EventData event) {
        if (event.getEventType() == getEventType()) {
            onEventTriggered(event);
        }
    }
}
