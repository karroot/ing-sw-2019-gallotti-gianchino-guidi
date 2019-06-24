package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class RequestRespawnTerminator extends  RequestInput {
    private int targetSpawnPoint;
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {
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

        System.out.println("Scegli il colore dello square dove spawnare:");


            System.out.println("1: rosso");
            System.out.println("2: blu");
            System.out.println("3: giallo");

        targetSpawnPoint  = inputInt(1, i - 1);



    }
}
