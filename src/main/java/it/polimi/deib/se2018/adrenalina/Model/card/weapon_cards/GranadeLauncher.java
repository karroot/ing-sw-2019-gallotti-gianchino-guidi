package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;


public class GranadeLauncher extends WeaponCard
{


    public GranadeLauncher(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

}