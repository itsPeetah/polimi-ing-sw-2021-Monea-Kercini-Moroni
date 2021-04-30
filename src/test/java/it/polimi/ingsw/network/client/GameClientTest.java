package it.polimi.ingsw.network.client;

import static org.junit.jupiter.api.Assertions.*;

class GameClientTest {

    public static void main(String[] args) {

        GameClient client = new GameClient("localhost", 42069);
        client.execute();

    }
}