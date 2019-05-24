package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

public class ResponseShootPeople extends ResponseInput
{
    private String targetBasicMode;//Target for basic mode
    /**
     * Create the response message for ShootPeople in basic mode
     * @param targetBasicMode target weapon for shooting
     */
    public ResponseShootPeople(String targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }
    /**
     * @return get the target for ShootPeople in basic mode
     */
    public String getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
