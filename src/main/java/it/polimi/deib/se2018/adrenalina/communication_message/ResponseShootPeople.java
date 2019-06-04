package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseShootPeople extends ResponseInput
{
    private int targetBasicMode;//Target for basic mode
    /**
     * Create the response message for ShootPeople in basic mode
     * @param targetBasicMode target weapon for shooting
     */
    public ResponseShootPeople(int targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }
    /**
     * @return get the target for ShootPeople in basic mode
     */
    public int getTargetBasicMode()
    {
        return targetBasicMode;
    }
}



