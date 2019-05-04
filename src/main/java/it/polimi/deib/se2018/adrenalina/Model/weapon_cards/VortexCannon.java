package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;


public class VortexCannon extends WeaponCard implements DoDamage, MoveTarget
{

    public VortexCannon( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }

    @Override
    public void doDamage(Player player, int quantity) {

    }

    @Override
    public void moveTarget(Player player, int x, int y) throws SquareNotInGameBoard {

    }
}