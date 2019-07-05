package it.polimi.deib.se2018.adrenalina.communication_message;

/**
 * This class implements the response for respawning.
 *
 * @author Karroot
 */
public class ResponseRespawn extends  ResponseInput {
    private int targetSpawnPoint;//Target for basic mode
    /**
     * Create the response message for respawn
     * @param targetSpawnPoint target for respawn
     */
    public ResponseRespawn(int targetSpawnPoint)
    {
        this.targetSpawnPoint = targetSpawnPoint;

    }
    /**
     * @return get the target for respawn
     */
    public int getTargetSpawnPoint()
    {
        return targetSpawnPoint;
    }
}
