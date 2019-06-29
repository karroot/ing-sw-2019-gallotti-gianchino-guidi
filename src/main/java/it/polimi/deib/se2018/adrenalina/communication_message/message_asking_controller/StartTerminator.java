package it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller;

import it.polimi.deib.se2018.adrenalina.View.Terminal;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

public class StartTerminator extends RequestInput {
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
