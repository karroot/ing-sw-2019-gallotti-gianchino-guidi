package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

public class RequestRailgun extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private List<ColorId> targetsAlternativeMode = new LinkedList<>();//Targets chosen for the alternative mode


    @Override
    protected void inputAlternativeMode() {

    }

    @Override
    protected void inputBasicMode() {

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }
}
