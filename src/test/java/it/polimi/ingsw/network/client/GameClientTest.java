package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameClientTest {

    public static void main(String[] args) {

        GameClient client = new GameClient("localhost", 42069);
        Scanner stdin = new Scanner(System.in);
        String clientMessage;
        boolean done = false;
        client.execute();

        while(client.isRunning()) {

            if(!done) {
                clientMessage = stdin.nextLine();
                if (ConnectionMessage.QUIT.check(clientMessage)) {
                    done = true;
                }
                if (clientMessage.charAt(0) == '/') {
                    client.send(new NetworkPacket(NetworkPacketType.DEBUG, clientMessage));
                } else {
                    client.send(new NetworkPacket(NetworkPacketType.SYSTEM, clientMessage));
                }
            }
        }

        client.stop();
    }
}