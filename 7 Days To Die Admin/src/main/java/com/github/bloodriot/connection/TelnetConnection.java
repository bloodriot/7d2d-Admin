package com.github.bloodriot.connection;

import java.io.IOException;
import java.net.SocketException;

import com.github.bloodriot.configuration.Configuration;

import org.apache.commons.net.telnet.TelnetClient;

public final class TelnetConnection extends ServerConnection {
    private final TelnetClient client;

    public TelnetConnection(Configuration configuration) {
        super(configuration);
        client = new TelnetClient();
    }

    @Override
    public Boolean connect() {
        try {
            client.connect(
                getConfiguration().getProperty("hostname"),
                Integer.parseInt(getConfiguration().getProperty("port"))
            );
        } catch (SocketException error) {
            // Handle Exception
            error.printStackTrace();
            return false;

        } catch (IOException error) {
            // Handle Exception
            error.printStackTrace();
            return false;
        } catch (NumberFormatException error) {
            error.printStackTrace();
            return false;
        }
        setConnected(true);
        return true;
    }
}
