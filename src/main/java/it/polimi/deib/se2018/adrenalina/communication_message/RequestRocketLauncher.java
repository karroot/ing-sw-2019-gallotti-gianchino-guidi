package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

/**
 * @author giovanni
 */
public class RequestRocketLauncher extends RequestInput
{
    private List<ColorId> colorIdList;


    private ColorId targetPlayerBasicMode;
    private String[] orderEffect;

    @Override
    public void printActionsAndReceiveInput() {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
