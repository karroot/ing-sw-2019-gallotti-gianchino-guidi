package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

public class PowerGlow extends WeaponCard
{


    public PowerGlow( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
        redAmmoCost = 0;
    }

}