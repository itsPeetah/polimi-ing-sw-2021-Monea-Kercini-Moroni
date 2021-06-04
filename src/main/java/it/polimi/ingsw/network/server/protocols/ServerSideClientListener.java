package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.server.components.RemoteUser;
import it.polimi.ingsw.network.server.components.UserTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSideClientListener {

    private RemoteUser user;
    private boolean done;
    private boolean continueAfterReturning;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ServerSideClientListener(RemoteUser user){
        this.user = user;
        System.out.println("Listening for " + user.getId());
    }

    public boolean run() {

        this.done = false;
        this.continueAfterReturning = false;

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true){
            if(done) break;

            NetworkPacket np = user.receive();

            if(np == null) break;

            switch (np.getPacketType()){
                case SYSTEM:
                    handleSystemMessage(np);
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    break;
                case ACTION:
                    // TODO thread?
                    executorService.submit(() -> handleActionPacket(np));
                    break;
                case SOCIAL:
                    // TODO thread?
                    executorService.submit(() -> handleSocialPacket(np));
                    break;
            }
        }

        executorService.shutdown();

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
        else if (GameLobbyMessage.LEAVE_ROOM.check(clientMessage)){
            System.out.println("Leave room");
            user.leaveCurrentRoom();
            done = true;
            continueAfterReturning = true;
            return;
        }
        // START
        else if (GameLobbyMessage.START_ROOM.check(clientMessage)) {
            executorService.submit(() -> user.getRoom().startGame());
            System.out.println( ANSIColor.GREEN +"[Server] Started game in room '" + user.getRoom().getId() + "'." + ANSIColor.RESET);
            return;
        }
        // PING
        else if(ConnectionMessage.PING.check(clientMessage)){
            user.respondedToPing();
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        System.out.println("[Debug] User "+ user.getId() + ": " + packet.getPayload());
    }

    private void handleActionPacket(NetworkPacket packet){
        executorService.submit(()-> user.getRoom().notify(packet));
    }

    private void handleSocialPacket(NetworkPacket packet){
        user.getRoom().handleSocialPacket(packet);
    }
}
