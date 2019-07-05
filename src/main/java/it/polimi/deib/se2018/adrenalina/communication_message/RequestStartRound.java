package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.ResponseStartRound;
import it.polimi.deib.se2018.adrenalina.View.Terminal;
/**
 * This class represents the request that the controller sends at the client if the player decided
 * to start his round
 * @author Cysko7927
 */
public class RequestStartRound extends  RequestInput {
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return new ResponseStartRound();
    }

}
