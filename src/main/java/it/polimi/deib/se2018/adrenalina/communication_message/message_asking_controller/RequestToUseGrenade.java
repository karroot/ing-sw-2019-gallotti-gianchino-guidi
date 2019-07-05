package it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller;

import it.polimi.deib.se2018.adrenalina.View.Terminal;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
/**
 * This class being used Controller to warn the view that the player must choose a grenade venom to use
 * @author Cysko7927
 */
public class RequestToUseGrenade extends RequestInput
{
    /**
     *
     * Not used
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
