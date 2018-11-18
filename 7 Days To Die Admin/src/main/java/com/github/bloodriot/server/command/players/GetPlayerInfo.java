package com.github.bloodriot.server.command.players;

import com.github.bloodriot.connection.ServerConnection;
import com.github.bloodriot.server.command.ServerCommand;

public class GetPlayerInfo extends ServerCommand {
    @Override
    public void runCommand(final ServerConnection server) {
        // 2018-04-05T22:58:11 24297.530 INF Executing command 'lp' by Telnet from 127.0.0.1:56764
        // 0. id=190, Player, pos=(-579.3, 62.1, 1124.7), rot=(0.0, 642.7, 0.0), remote=True, health=105, deaths=3, zombies=805, players=0, score=790, level=149
        // Total of 1 in the game
        String command = "lp";
        server.execute(command);
    }
}