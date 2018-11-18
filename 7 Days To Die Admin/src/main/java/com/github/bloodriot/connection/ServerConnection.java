package com.github.bloodriot.connection;

import com.github.bloodriot.configuration.Configuration;
import com.google.inject.Inject;

public abstract class ServerConnection {

    private Boolean connected = false;
    private final Configuration configuration;

    @Inject
    public ServerConnection(final Configuration configuration) {
        this.configuration = configuration;
    }

    public Boolean connect() {
        return false;
    }

    public void setConnected(final Boolean connected) {
        this.connected = connected;
    }
    public Boolean connected() {
        return connected;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public void execute(final String command) {
    }
}
