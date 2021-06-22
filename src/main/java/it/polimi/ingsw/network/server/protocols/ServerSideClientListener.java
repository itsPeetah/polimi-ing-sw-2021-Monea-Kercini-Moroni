package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.server.components.GameRoom;
import it.polimi.ingsw.network.server.components.RemoteUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class handling the reception of messages from the remote client.
 */
public class ServerSideClientListener {

    private RemoteUser user;
    private boolean done;
    private boolean continueAfterReturning;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Class constructor.
     * @param user Remote client's user reference.
     */
    public ServerSideClientListener(RemoteUser user){
        this.user = user;
        System.out.println("Listening for " + user.getId());
    }

    /**
     * Actual listening procedure/method.
     * @return whether the client has disconnected or not after this phase is over.
     */
    public boolean run() {

        this.done = false;
        this.continueAfterReturning = false;

        // Thread pool allocation for the processing of certain messages.
        ExecutorService executorService = Executors.newCachedThreadPool();

        // If the player was the last one to join and the room is full, start the game automatically
        if(user.getRoom().isFull())
            executorService.submit(()->user.getRoom().startGame());

        while (true){
            if(done) break;

            NetworkPacket np = user.receive();

            // NP == NULL <=> the connection has been interrupted
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

        executorService.shutdown();

        return continueAfterReturning;
    }

    /**
     * Handle system message NPs
     */
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
            executorService.submit(() -> user.getRoom().startGame());
            System.out.println( ANSIColor.GREEN +"[Server] Started game in room '" + user.getRoom().getId() + "'." + ANSIColor.RESET);
            return;
        }
        // PING
        else if(SystemMessage.PING.check(messageCode)){
            user.respondedToPing();
        }
    }

    /**
     * Handle debug NPs
     */
    private void handleDebugMessage(NetworkPacket packet){
        System.out.println("[Debug] User "+ user.getId() + ": " + packet.getPayload());
    }

    /**
     * Handle action NPs
     */
    private void handleActionPacket(NetworkPacket packet){
        executorService.submit(()-> user.getRoom().notify(packet));
    }

    /**
     * Handle social message NPs
     */
    private void handleSocialPacket(NetworkPacket packet){
        user.getRoom().handleSocialPacket(packet);
    }
}
