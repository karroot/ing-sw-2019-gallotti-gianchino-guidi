package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to use the weapon ElectroSchyte
 * @author Karroot
 */
public class RequestElectroSchyte extends WeaponWithModeAlternative {
    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode



    /**
     * Create a message of request for the weapon ElectroSchyte
     * @param availableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAlternativeMode targets for the alternative mode
     */
    public RequestElectroSchyte(boolean[] availableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAlternativeMode)
    {
        this.nameAlternaivemode = "modalità mietitore";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;
        responseIsReady = false;
    }

    @Override
    protected void inputBasicMode() {

    }

    @Override
    protected void inputAlternativeMode() {

    }

    /**
     *Generate the response message for the Electro Schyte with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");


        return new ResponseElectroSchyte(playersBasicMode,mode);
    }
}
