package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;
import java.util.Set;

public class Death implements StatePlayer {


    public void calculateScore() {
    }


    public Death()
    {

    }

    @Override
    public Set<Square> lookForRunAround(Player player, GameBoard gameBoard) {
        return null;
    }

    @Override
    public Set<Square> lookForGrabStuff(Player player, GameBoard gameBoard) {
        return null;
    }

    @Override
    public List<Player> lookForShootPeople(Player player, GameBoard gameBoard) {
        return null;
    }

    @Override
    public List<WeaponCard> checkReload(Player player) {
        return null;
    }
}