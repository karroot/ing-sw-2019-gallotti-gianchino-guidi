package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Set;

public class TerminatorState extends StatePlayer {
    @Override
    public Set<Square> lookForRunAround(Player player) {
        Set<Square>  squareSet;
        squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        return squareSet;
    }

    @Override
    public Set<Square> lookForGrabStuff(Player player) {
        return null;
    }
}
