package com.github.bloodriot.server.command.time;

import com.github.bloodriot.connection.ServerConnection;
import com.github.bloodriot.server.command.ServerCommand;

public class GetTime extends ServerCommand {
    @Override
    public void runCommand(final ServerConnection server) {
        String command = "gettime";
        server.execute(command);
    }
}