package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class PowerGlow extends WeaponCard implements DoDamage, MarkTarget, MoveYourself
{


    public PowerGlow( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
        redAmmoCost = 0;
    }

    @Override
    public void doDamage(Player player, int quantity) {

    }

    public void markTarget()
    {

    }

    public void moveYourself()
    {

    }

}