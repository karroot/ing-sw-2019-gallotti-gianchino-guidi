package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

/**
 * This class represents an end message and being used by controller to say at the client
 * that there aren't more power up to use
 * @author Cysko7927
 */
public class End extends  RequestInput {
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
