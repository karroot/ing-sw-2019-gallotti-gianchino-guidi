package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseReloadWeapon extends ResponseInput
{
    private String targetWeaponToReload;//Target for basic mode

    public ResponseReloadWeapon(String targetWeaponToReload)
    {
        this.targetWeaponToReload = targetWeaponToReload;

    }

    public String getTargetWeaponToReload()
    {
        return targetWeaponToReload;
    }
}
