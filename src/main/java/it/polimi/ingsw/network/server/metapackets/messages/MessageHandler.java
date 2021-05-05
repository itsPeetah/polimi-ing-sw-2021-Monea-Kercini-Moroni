package it.polimi.ingsw.network.server.metapackets.messages;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* CODE EXAMPLE FOR HANDLER USAGE:

        // Send a message of type ERROR to the player with username "pIeTroMorOnS"
        MessageHandler.sendMessage("pIeTroMorOnS", Message.ERROR);

        Note that this handler is NOT BLOCKING, meaning that the action will be performed immediately and no thread will be blocked.

*/

public class MessageHandler {
    private static final BlockingQueue<MessagePacket> queue = new LinkedBlockingDeque<>();

    /**
     * Send a message to a player.
     * @param player player to whom the message is addressed.
     * @param message type of message.
     */
    public static void sendMessage(String player, Message message) {
        MessagePacket newPacket = new MessagePacket(player, message);
        queue.add(newPacket);
    }

    /**
     * Take a message to be sent to a player.
     * WARNING: if no message is present, the caller will be blocked forever.
     */
    public static MessagePacket takeMessage() throws InterruptedException {
        return queue.take();
    }

    /**
     * Take a message to be sent to a player.
     * WARNING: if no message is present, the caller will be blocked until the specified timeout is over.
     * When the timeout expires, null is returned.
     */
    public static MessagePacket pollMessage(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return queue.poll(timeout, timeUnit);
    }
}
