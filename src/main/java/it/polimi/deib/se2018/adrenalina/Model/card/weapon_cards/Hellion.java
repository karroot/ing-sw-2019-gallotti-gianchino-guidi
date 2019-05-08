package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;


public class Hellion extends WeaponCard
{

    public Hellion( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }


}