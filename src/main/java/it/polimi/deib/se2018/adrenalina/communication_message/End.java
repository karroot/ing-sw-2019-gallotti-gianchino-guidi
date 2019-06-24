package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class End extends  RequestInput {
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
