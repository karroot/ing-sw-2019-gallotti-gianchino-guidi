package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to respawn user
 * @author Karroot
 */


public class RespawnTerminator extends RequestInput
{

    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        return null;
    }
}
