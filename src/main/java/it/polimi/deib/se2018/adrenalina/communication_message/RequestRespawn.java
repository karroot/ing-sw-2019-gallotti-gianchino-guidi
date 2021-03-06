package it.polimi.deib.se2018.adrenalina.communication_message;



import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;
/**
 * This class represents the request that the conroller sends at the client if the player decided
 * to respawn
 * @author Karroot
 */
public class RequestRespawn extends  RequestInput
{
    //Attribute for the request
    protected List<Color> playerPowerUp;

    //Attribute for the response
    protected int targetSpawnPoint;


    /**
     * this method is used to set the player power up for respawn
     * @param playerPowerUp power up for respawn
     */
    public RequestRespawn(List<Color> playerPowerUp) {
        this.playerPowerUp = playerPowerUp;
    }
    /**
     * Ask at the user in which square he wants respawn
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    /**
     * this method return the response for respawn
     * @return response for respawn
     * @throws IllegalStateException if input are not taken yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");
        char ler=0;
        return new ResponseRespawn(targetSpawnPoint);
    }
    protected void inputBasicMode()
    {
        int i = 1;

        terminal.addTextInput("Scegli il colore dello square dove spawnare:");

        for (Color t:playerPowerUp)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        int anInt = terminal.inputInt(1, i - 1);

        targetSpawnPoint = anInt ;

    }
}
