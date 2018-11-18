package com.github.bloodriot;

import java.util.Locale;

import com.github.bloodriot.connection.ServerConnection;
import com.github.bloodriot.connection.TelnetConnection;
import com.github.bloodriot.regex.LogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerAdmin 
{
    private static String type = null;

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerAdmin.class);

    public static void main( String[] args ) {
        Provider provider = new Provider();
        type = provider.getConfiguration().getProperty("type");

        if (type == null) {
            LOGGER.error("Missing or invalid settings in config.properties.");
            return;
        }

        ServerConnection serverConnection;

        switch (type.toLowerCase(Locale.ENGLISH)) {
            case "telnet":
                serverConnection = provider.getInjector().getInstance(TelnetConnection.class);
            break;
            default:
                LOGGER.error("Unable to find a usable type provider. Check your config.properties type setting.");
                return;
        }

        provider.getEventBus().register(serverConnection);
        serverConnection.connect();
        LOGGER.info("Connected to server:" + serverConnection.connected());

        LogParser logParser = provider.getInjector().getInstance(LogParser.class);
        provider.getEventBus().register(logParser);
    }
}
