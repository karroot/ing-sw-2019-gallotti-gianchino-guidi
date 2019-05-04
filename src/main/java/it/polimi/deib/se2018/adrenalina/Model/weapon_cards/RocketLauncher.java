package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

public class RocketLauncher extends WeaponCard implements MoveTarget, DoDamage
{


    public RocketLauncher( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }

    @Override
    public void moveTarget(Player player, int x, int y) throws SquareNotInGameBoard {

    }

    @Override
    public void doDamage(Player player, int quantity) {

    }
}