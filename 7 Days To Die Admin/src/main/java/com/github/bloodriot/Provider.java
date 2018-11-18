package com.github.bloodriot;

import com.github.bloodriot.configuration.Configuration;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public final class Provider {
    private final Injector injector;
    private final Configuration configuration;
    private final EventBus eventBus;
    
    public Provider() {
        this.configuration = new Configuration();
        this.eventBus = new EventBus("Main");
        this.injector = Guice.createInjector(new AbstractModule(){
            @Override
            protected void configure() {
                bind(Configuration.class).toInstance(configuration);
                bind(EventBus.class).toInstance(eventBus);
            }
        });
    }

    public Provider(final Injector injector, final Configuration configuration, final EventBus eventBus) {
        this.injector = injector;
        this.configuration = configuration;
        this.eventBus = eventBus;
    }

    public Injector getInjector() {
        return this.injector;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public EventBus getEventBus() {
        return this.eventBus;
    }
}