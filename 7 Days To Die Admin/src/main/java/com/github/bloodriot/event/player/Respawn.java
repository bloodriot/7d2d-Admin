package com.github.bloodriot.event.player;

import java.util.regex.Matcher;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.Event;
import com.github.bloodriot.event.EventData;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class Respawn extends Event {

    @Inject
    public Respawn(final Configuration configuration, final EventBus eventBus) {
        super(configuration, eventBus);
        setEventType(EventTypes.PLAYER_RESPAWN);
    }

    @Override
    public void onEventTriggered(EventData event) {
        Matcher matcher = (Matcher) event.getData();
        String player = matcher.group(1);
        System.out.println("Player: " + player + " has respawned.");
    }

    @Subscribe
    public void listen(EventData event) {
        if (event.getEventType() == getEventType()) {
            onEventTriggered(event);
        }
    }
}