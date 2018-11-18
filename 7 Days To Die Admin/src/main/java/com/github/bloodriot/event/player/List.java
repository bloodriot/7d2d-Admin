package com.github.bloodriot.event.player;

import java.util.regex.Matcher;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.Event;
import com.github.bloodriot.event.EventData;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class List extends Event {

    @Inject
    public List(final Configuration configuration, final EventBus eventBus) {
        super(configuration, eventBus);
        setEventType(EventTypes.PLAYER_LIST);
    }

    @Override
    public void onEventTriggered(EventData event) {
        Matcher matcher = (Matcher) event.getData();
        String player = matcher.group(1);
        String health = matcher.group(2);
        String deaths = matcher.group(3);
        String zombies = matcher.group(4);
        String players = matcher.group(5);
        String score = matcher.group(6);
        String level = matcher.group(7);

        System.out.println("Player: " + player + "Level: " + level + " Health: " + health + " Zombie Kills: " + zombies + " Player Kills: " + players + " Deaths: " + deaths + " Score: " + score);
    }

    @Subscribe
    public void listen(EventData event) {
        if (event.getEventType() == getEventType()) {
            onEventTriggered(event);
        }
    }
}