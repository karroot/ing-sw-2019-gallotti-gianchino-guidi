package it.polimi.deib.se2018.adrenalina.communication_message;

public class RequestTagbackGranade extends RequestInput
{
    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTagbackGranade(targetBasicMode);
    }
}
