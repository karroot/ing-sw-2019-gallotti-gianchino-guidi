package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseShootPeople extends ResponseInput
{
    private int chosenWeapon;//Target for basic mode
    /**
     * Create the response message for ShootPeople in basic mode
     * @param chosenWeapon target weapon for shooting
     */
    public ResponseShootPeople(int chosenWeapon)
    {
        this.chosenWeapon = chosenWeapon;

    }
    /**
     * @return get the target for ShootPeople in basic mode
     */
    public int getChosenWeapon()
    {
        return chosenWeapon;
    }
}



