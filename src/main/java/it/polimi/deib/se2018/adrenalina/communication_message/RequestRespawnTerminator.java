package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class RequestRespawnTerminator extends  RequestInput {
    private int targetSpawnPoint;
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
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

        terminal.addTextInput("Scegli il colore dello square dove spawnare:");


            terminal.addOptionInput("1: rosso");
            terminal.addOptionInput("2: blu");
            terminal.addOptionInput("3: giallo");

        targetSpawnPoint  = inputInt(1, i - 1);



    }
}
