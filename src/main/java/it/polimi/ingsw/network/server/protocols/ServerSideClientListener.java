package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.server.components.GameRoom;
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
        System.out.println("Listening for " + user.getId());
    }

    public boolean run() {

        this.done = false;
        this.continueAfterReturning = false;

        ExecutorService executorService = Executors.newCachedThreadPool();

        if(user.getRoom().isFull())
            executorService.submit(()->user.getRoom().startGame());

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
                    executorService.submit(() -> handleActionPacket(np));
                    break;
                case SOCIAL:
                    executorService.submit(() -> handleSocialPacket(np));
                    break;
            }
        }

        // TODO Using two executors -> ONLY NEED 1

        executorService.shutdown();

        return continueAfterReturning;
    }

    private void handleSystemMessage(NetworkPacket packet){
        String[] clientMessage = packet.getPayload().split(" ");
        String messageCode = clientMessage[0];

        // QUIT
        if(SystemMessage.QUIT.check(messageCode)){
            done = true;
            continueAfterReturning = false;
            return;
        }
        // LEAVE
        else if (SystemMessage.LEAVE_ROOM.check(messageCode)){
            user.leaveCurrentRoom();
            done = true;
            continueAfterReturning = true;
            return;
        }
        // START
        else if (SystemMessage.START_ROOM.check(messageCode)) {

            /*GameRoom gr = user.getRoom();
            new Thread(() -> gr.startGame());*/
            executorService.submit(() -> user.getRoom().startGame());
            System.out.println( ANSIColor.GREEN +"[Server] Started game in room '" + user.getRoom().getId() + "'." + ANSIColor.RESET);
            return;
        }
        // PING
        else if(SystemMessage.PING.check(messageCode)){
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
