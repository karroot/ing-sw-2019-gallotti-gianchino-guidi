package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.ResponseStartRound;

public class RequestStartRound extends  RequestInput {
    @Override
    public void printActionsAndReceiveInput() {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return new ResponseStartRound();
    }

}
