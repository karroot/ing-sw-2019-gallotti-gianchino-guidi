package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class Flamethrower extends WeaponCard implements DoDamage
{


    public Flamethrower( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);

        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    @Override
    public void doDamage(Player player, int quantity) {

    }
}