package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.ResponseStartRound;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class RequestStartRound extends  RequestInput {
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return new ResponseStartRound();
    }

}
