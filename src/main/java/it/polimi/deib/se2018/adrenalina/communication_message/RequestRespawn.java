package it.polimi.deib.se2018.adrenalina.communication_message;



import it.polimi.deib.se2018.adrenalina.Model.Color;

import java.util.List;

public class RequestRespawn extends  RequestInput
{
    //Attribute for the request
    protected List<Color> playerPowerUp;//Targets for the basic mode

    //Attribute for the response
    protected int targetSpawnPoint;//Target chosen for the basic mode



    public RequestRespawn(List<Color> playerPowerUp) {
        this.playerPowerUp = playerPowerUp;
    }

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
        throw new IllegalStateException("Input non ancora presi");

        return new ResponseRespawn(targetSpawnPoint);
    }
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un bersaglio:");

        for (Color t:playerPowerUp)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetSpawnPoint = anInt ;

    }
}
