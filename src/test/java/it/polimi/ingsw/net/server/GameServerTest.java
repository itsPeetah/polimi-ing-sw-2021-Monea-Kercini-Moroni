package it.polimi.ingsw.net.server;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameServerTest {

    GameServer gameServer;

    @Test
    public void testServerStart() throws IOException {
        gameServer = new GameServer("localhost", 36676);
        System.out.println("Starting server.");
        gameServer.startServer();
    }

    @Test
    public void testIdGeneration(){

        gameServer = new GameServer("localHost", 36676);
        System.out.println(gameServer.getNewClientId());
        System.out.println(gameServer.getNewClientId());
        System.out.println(gameServer.getNewClientId());
        System.out.println(gameServer.getNewClientId());
    }
}