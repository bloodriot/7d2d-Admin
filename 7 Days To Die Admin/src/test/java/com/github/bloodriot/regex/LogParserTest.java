package com.github.bloodriot.regex;

import static org.junit.Assert.assertEquals;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.EventTypes;
import com.github.bloodriot.event.game.Time;
import com.github.bloodriot.event.horde.End;
import com.github.bloodriot.event.horde.Night;
import com.github.bloodriot.event.horde.Start;
import com.github.bloodriot.event.horde.Warning;
import com.github.bloodriot.event.player.Chat;
import com.github.bloodriot.event.player.Death;
import com.github.bloodriot.event.player.Join;
import com.github.bloodriot.event.player.Leave;
import com.github.bloodriot.event.player.List;
import com.github.bloodriot.event.player.Respawn;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Test;

public final class LogParserTest {

    private final Injector injector;

    public LogParserTest() {
        Configuration configuration = new Configuration();
        EventBus eventBus = new EventBus("Test");

        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(EventBus.class).toInstance(eventBus);
                bind(Configuration.class).toInstance(configuration);
            }
        });

        eventBus.register(injector.getInstance(Join.class));
        eventBus.register(injector.getInstance(Leave.class));
        eventBus.register(injector.getInstance(Chat.class));
        eventBus.register(injector.getInstance(Death.class));
        eventBus.register(injector.getInstance(Respawn.class));
        eventBus.register(injector.getInstance(List.class));
        eventBus.register(injector.getInstance(End.class));
        eventBus.register(injector.getInstance(Night.class));
        eventBus.register(injector.getInstance(Start.class));
        eventBus.register(injector.getInstance(Warning.class));
        eventBus.register(injector.getInstance(Time.class));
    }
    
    @Test
    public void testPlayerLeaveEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("Player testPlayer disconnected after 78.6 minutes");
        assertEquals("PLAYER_LEAVE event parses correctly", EventTypes.PLAYER_LEAVE, event);
    }

    @Test
    public void testPlayerChatEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("Chat: 'player': test");
        assertEquals("PLAYER_CHAT event parses correctly", EventTypes.PLAYER_CHAT, event);
    }

    @Test
    public void testPlayerRespawnEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("PlayerSpawnedInWorld (reason: Died, position: -251, 56, 983): EntityID=1264, PlayerID='76561198079856347', OwnerID='76561198079856347', PlayerName='player'");
        assertEquals("PLAYER_RESPAWN event parses correctly", EventTypes.PLAYER_RESPAWN, event);
    }

    @Test
    public void testPlayerDeathEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("GMSG: Player 'player' died");
        assertEquals("PLAYER_DEATH event parses correctly", EventTypes.PLAYER_DEATH, event);
    }

    @Test
    public void testPlayerJoinEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("PlayerSpawnedInWorld (reason: JoinMultiplayer, position: 1380, 57, 900): EntityID=190, PlayerID='76561198035800464', OwnerID='76561198035800464', PlayerName='player'");
        assertEquals("PLAYER_JOIN event parses correctly", EventTypes.PLAYER_JOIN, event);
    }

    @Test
    public void testPlayerListEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("0. id=190, player, pos=(-579.3, 62.1, 1124.7), rot=(0.0, 642.7, 0.0), remote=True, health=105, deaths=3, zombies=805, players=0, score=790, level=149");
        assertEquals("PLAYER_LIST event parses correctly", EventTypes.PLAYER_LIST, event);
    }

    @Test
    public void testGameTimeEvent() {
        LogParser parser = injector.getInstance(LogParser.class);
        EventTypes event = parser.parseLine("Day 45, 01:38");
        assertEquals("GAME_TIME event parses correctly", EventTypes.GAME_TIME, event);
    }
}
