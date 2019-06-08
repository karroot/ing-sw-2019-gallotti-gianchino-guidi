package it.polimi.deib.se2018.adrenalina.communication_message;

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
