package it.polimi.deib.se2018.adrenalina.Model.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public interface MoveTarget
{


    public void moveTarget(Player player, int x, int y, GameBoard arena);

}