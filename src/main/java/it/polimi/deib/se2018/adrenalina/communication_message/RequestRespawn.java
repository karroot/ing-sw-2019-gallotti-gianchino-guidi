package it.polimi.deib.se2018.adrenalina.communication_message;



import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

public class RequestRespawn extends  RequestInput
{
    //Attribute for the request
    protected List<Color> playerPowerUp;

    //Attribute for the response
    protected int targetSpawnPoint;



    public RequestRespawn(List<Color> playerPowerUp) {
        this.playerPowerUp = playerPowerUp;
    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

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
