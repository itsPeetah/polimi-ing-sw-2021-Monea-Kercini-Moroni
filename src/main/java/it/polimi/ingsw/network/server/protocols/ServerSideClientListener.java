package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.server.components.RemoteUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSideClientListener {

    private RemoteUser user;
    private boolean done;
    private boolean continueAfterReturning;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ServerSideClientListener(RemoteUser user){
        this.user = user;
    }

    public boolean run() {

        this.done = false;
        this.continueAfterReturning = false;

        while (true){
            if(done) break;

            NetworkPacket np = user.receive();
            switch (np.getPacketType()){
                case SYSTEM:
                    handleSystemMessage(np);
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    break;
                case ACTION:
                    handleActionPacket(np);
                    break;
                case SOCIAL:
                    handleSocialPacket(np);
                    break;
            }
        }

        return continueAfterReturning;
    }

    private void handleSystemMessage(NetworkPacket packet){
        String clientMessage = packet.getPayload();

        // QUIT
        if(ConnectionMessage.QUIT.check(clientMessage)){
            done = true;
            continueAfterReturning = false;
            return;
        }
        // LEAVE
        // TODO Check whether the user can do this (game not started yet).
        else if (GameLobbyMessage.LEAVE_ROOM.check(clientMessage)){
            user.leaveCurrentRoom();
            done = true;
            continueAfterReturning = true;
            return;
        }
        // START
        else if (GameLobbyMessage.START_ROOM.check(clientMessage)) {
            System.out.println("Starting room");
            executorService.submit(() -> user.getRoom().startGame());
            System.out.println("Started room");
            return;
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        /*String clientMessage = packet.getPayload();*/
        System.out.println("[USER "+ user.getId() + "] " + packet.getPayload());
        /*clientMessage = "[SERVER ECHO] You said: " + clientMessage;*/
        /*user.send(new NetworkPacket(NetworkPacketType.DEBUG, clientMessage));*/
    }

    private void handleActionPacket(NetworkPacket packet){
        user.getRoom().notify(packet);
    }

    private void handleSocialPacket(NetworkPacket packet){
        System.out.println("Received SocialMessage");
        user.getRoom().handleSocialPacket(packet);
    }
}
