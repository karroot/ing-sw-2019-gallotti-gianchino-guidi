package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponsePowerUp extends ResponseInput {
    private List<String> chosenPowerUpList ;//Target for basic mode

    public ResponsePowerUp(List<String> chosenPowerUpList) {
        this.chosenPowerUpList = chosenPowerUpList;
    }

    /**
     * @return get the index of the chosen powerup
     */
    public List<String> getChosenPowerUpList()
    {
        return chosenPowerUpList;
    }
}
