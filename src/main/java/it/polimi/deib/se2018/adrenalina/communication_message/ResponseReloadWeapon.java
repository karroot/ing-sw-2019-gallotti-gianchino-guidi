package it.polimi.deib.se2018.adrenalina.communication_message;
/**
 * This class implements the response for this reloading.
 *
 * @author Karroot
 */
public class ResponseReloadWeapon extends ResponseInput
{
    private int targetWeaponToReload;//Target for basic mode

    public ResponseReloadWeapon(int targetWeaponToReload)
    {
        this.targetWeaponToReload = targetWeaponToReload;

    }

    public int getTargetWeaponToReload()
    {
        return targetWeaponToReload;
    }
}
