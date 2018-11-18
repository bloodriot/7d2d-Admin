package com.github.bloodriot.event.game;

import static org.junit.Assert.assertEquals;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Test;

public class TimeTest {
    
    private final Injector injector;

    public TimeTest() {
        Configuration configuration = new Configuration();
        EventBus eventBus = new EventBus("Test");

        injector = Guice.createInjector(new AbstractModule(){
            @Override
            protected void configure() {
                bind(EventBus.class).toInstance(eventBus);
                bind(Configuration.class).toInstance(configuration);
            }
        });
    }
    @Test
    public void testHordeWarnings() {
        Time time = injector.getInstance(Time.class);
        assertEquals("Day 7 alert works as expected.", EventTypes.HORDE_NIGHT, time.hordeWarning(7, 0, 0));
        assertEquals("Day 7 warning works as expected.", EventTypes.HORDE_WARNING, time.hordeWarning(7, 21, 0));
        assertEquals("Day 7 start works as expected.", EventTypes.HORDE_START, time.hordeWarning(7, 22, 0));
        assertEquals("Day 7 end works as expected.", EventTypes.HORDE_END, time.hordeWarning(8, 4, 0));
        assertEquals("Day 7 alert works as expected.", EventTypes.NONE, time.hordeWarning(2, 21, 0));
    }
}
