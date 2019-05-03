package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

public interface MoveTarget
{


    public void moveTarget(Player player, int x, int y) throws SquareNotInGameBoard;

}