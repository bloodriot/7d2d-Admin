package com.github.bloodriot.event;

import com.github.bloodriot.configuration.Configuration;
import com.google.common.eventbus.EventBus;

public abstract class Event {
    private EventTypes eventType;
    private final Configuration configuration;
    private final EventBus eventBus;

    public Event(final Configuration configuration, final EventBus eventBus) {
        this.configuration = configuration;
        this.eventBus = eventBus;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }
    
    public void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    public EventTypes getEventType() {
        return this.eventType;
    }
    
    public void onEventTriggered(EventData data) {
        //Player bloodriot disconnected after 78.6 minutes
        //Chat: 'bloodriot': test
        //GMSG: Player 'bloodriot' died
        // INF PlayerSpawnedInWorld (reason: Died, position: -251, 56, 983): EntityID=1264, PlayerID='76561198079856347', OwnerID='76561198079856347', PlayerName='swarren102'
        // INF PlayerSpawnedInWorld (reason: JoinMultiplayer, position: 1380, 57, 900): EntityID=190, PlayerID='76561198035800464', OwnerID='76561198035800464', PlayerName='Spof'
        // INF Executing command 'gettime' by Telnet from 127.0.0.1:56764
        // Day 83, 01:38
        // 2018-04-05T22:58:11 24297.530 INF Executing command 'lp' by Telnet from 127.0.0.1:56764
        // 0. id=190, Spof, pos=(-579.3, 62.1, 1124.7), rot=(0.0, 642.7, 0.0), remote=True, health=105, deaths=3, zombies=805, players=0, score=790, level=149, steamid=76561198035800464, ip=73.149.217.224, ping=29
        // Total of 1 in the game
    }
}
