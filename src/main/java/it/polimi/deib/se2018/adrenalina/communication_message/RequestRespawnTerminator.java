package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to respawn the terminator
 * @author Karroot
 */
public class RequestRespawnTerminator extends  RequestInput {
    private int targetSpawnPoint;
    /**
     * Ask at the user in which square he wants to move
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    /**
     * this method generate the response message fot respawn
     * @return response respawn
     * @throws IllegalStateException if the inputs there weren't taken
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        char asw=0;
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseRespawn(targetSpawnPoint);
    }

    protected void inputBasicMode()
    {
        terminal.addTextInput("Scegli il colore dello square dove spawnare il terminator:");


        terminal.addOptionInput("1: rosso");
        terminal.addOptionInput("2: blu");
        terminal.addOptionInput("3: giallo");

        targetSpawnPoint  = terminal.inputInt(1, 3);

    }
}
