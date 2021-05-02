package it.polimi.ingsw.network.server.metapackets.updates;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* CODE EXAMPLE FOR HANDLER USAGE:

        // Send an update of type EMPTY with the associated data EmptyUpdateData
        UpdateHandler.pushUpdate(Update.EMPTY, data);

        Note that this handler is NOT BLOCKING, meaning that the action will be performed immediately and no thread will be blocked.

*/

public class UpdateHandler {
    private static final BlockingQueue<UpdatePacket> queue = new LinkedBlockingDeque<>();

    /**
     * Push an update to be sent to all players.
     */
    public static void pushUpdate(Update type, UpdateData data) {
        String jsonData = type.parseData(data);
        UpdatePacket updatePacket = new UpdatePacket(type, jsonData);
        queue.add(updatePacket);
    }

    /**
     * Take an update ready to be notified to all players.
     * WARNING: if no update is present, the caller will be blocked forever.
     */
    public static UpdatePacket takeUpdate() throws InterruptedException {
        return queue.take();
    }

    /**
     * Take an update ready to be notified to all players.
     * WARNING: if no update is present, the caller will be blocked until the specified timeout is over.
     * When the timeout expires, null is returned.
     */
    public static UpdatePacket pollUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return queue.poll(timeout, timeUnit);
    }
}
