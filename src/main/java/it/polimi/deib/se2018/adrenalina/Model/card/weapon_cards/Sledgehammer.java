package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;


public class Sledgehammer extends WeaponCard
{


    public Sledgehammer(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 0;
    }



}