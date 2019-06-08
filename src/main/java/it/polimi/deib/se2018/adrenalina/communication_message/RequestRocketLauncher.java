package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.SpawnPoint;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.List;

/**
 * @author giovanni
 */
public class RequestRocketLauncher extends RequestInput {
    private List<ColorId> colorIdListBasicMode;
    private List<ColorId> colorIdListWithFragmentingWarhead;


    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove;
    private String targetSquareCoordinatesAsStringTargetToMove;
    private String[] orderEffect;

    public RequestRocketLauncher (boolean[] availableMethod, )


    @Override
    public void printActionsAndReceiveInput() {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }

}
