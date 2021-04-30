package it.polimi.ingsw.network.server;

import static org.junit.jupiter.api.Assertions.*;

class GameServerTest {
    public static void main(String[] args){
        GameServer server = new GameServer("localhost", 42069).setAsInstance();
        server.execute();
    }
}