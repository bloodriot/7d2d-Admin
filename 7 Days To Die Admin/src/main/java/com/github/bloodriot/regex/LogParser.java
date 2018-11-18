package com.github.bloodriot.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.bloodriot.event.EventData;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public final class LogParser {
    EventBus eventBus;

    @Inject
    public LogParser(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void listen(EventData event) {
        if (event.getEventType() == EventTypes.DATA_STREAM) {
            parseLine((String) event.getData());
        }
    }

    public EventTypes parseLine(final String line) {
        Pattern pattern;
        Matcher matcher;
        /**
        PLAYER_LEFT
        Player bloodriot disconnected after 78.6 minutes
        */
        pattern = Pattern.compile(".*Player (.*) disconnected after ([0-9]+[.]*[0-9]*) minutes");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_LEAVE, matcher));
            return EventTypes.PLAYER_LEAVE;
        }

        /**
        PLAYER_CHAT
        Chat: 'player': test
        */
        pattern = Pattern.compile(".*Chat: '(.+)': (.+)");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_CHAT, matcher));
            return EventTypes.PLAYER_CHAT;
        }

        /**
        PLAYER_RESPAWN
        PlayerSpawnedInWorld (reason: Died, position: -251, 56, 983): EntityID=1264, PlayerID='76561198079856347', OwnerID='76561198079856347', PlayerName='player'
        */
        pattern = Pattern.compile(".*PlayerSpawnedInWorld \\(reason: Died, .+PlayerName='(.+)'");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_RESPAWN, matcher));
            return EventTypes.PLAYER_RESPAWN;
        }

        /**
        PLAYER_DEATH
        GMSG: Player 'player' died
        */
        pattern = Pattern.compile(".*Player '(.+)' died");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_DEATH, matcher));
            return EventTypes.PLAYER_DEATH;
        }

        /**
        PLAYER_JOIN
        PlayerSpawnedInWorld (reason: JoinMultiplayer, position: 1380, 57, 900): EntityID=190, PlayerID='76561198035800464', OwnerID='76561198035800464', PlayerName='player'
        */
        pattern = Pattern.compile(".*PlayerSpawnedInWorld \\(reason: JoinMultiplayer, .+PlayerName='(.+)'");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_JOIN, matcher));
            return EventTypes.PLAYER_JOIN;
        }

        /**
        GAME_TIME
        Day 83, 01:38
        */
        pattern = Pattern.compile(".*Day ([0-9]+), ([0-9]+):([0-9]+)");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.GAME_TIME, matcher));
            return EventTypes.GAME_TIME;
        }

        /**
        PLAYER_LIST
        0. id=190, player, pos=(-579.3, 62.1, 1124.7), rot=(0.0, 642.7, 0.0), remote=True, health=105, deaths=3, zombies=805, players=0, score=790, level=149
        */
        pattern = Pattern.compile(".*[0-9]+[.] id=[0-9]+, (.+), pos=.+ health=([0-9]+), deaths=([0-9]+), zombies=([0-9]+), players=([0-9]+), score=([0-9]+), level=([0-9]+)");
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            eventBus.post(new EventData(EventTypes.PLAYER_LIST, matcher));
            return EventTypes.PLAYER_LIST;
        }

        return EventTypes.UNKNOWN;
    }
}
