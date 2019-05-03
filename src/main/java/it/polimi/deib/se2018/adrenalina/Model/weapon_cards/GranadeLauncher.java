package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class GranadeLauncher extends WeaponCard implements MoveTarget, DoDamage
{


    public GranadeLauncher(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    public void moveTarget()
    {

    }

    @Override
    public void doDamage(Player player, int quantity) {

    }
}