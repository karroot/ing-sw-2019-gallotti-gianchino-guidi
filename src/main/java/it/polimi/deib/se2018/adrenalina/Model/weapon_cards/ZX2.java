package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

public class ZX2 extends WeaponCard implements DoDamage, MarkTarget
{


    public ZX2(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    @Override
    public void doDamage(Player player, int quantity) {

    }

    public void markTarget()
    {

    }

}