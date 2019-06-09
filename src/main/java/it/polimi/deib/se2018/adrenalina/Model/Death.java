package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.List;
import java.util.Set;

public class Death extends StatePlayer {


    public void calculateScore() {
    }


    public Death()
    {

    }

    @Override
    public Set<Square> lookForRunAround(Player player) {
        return null;
    }

    @Override
    public Set<Square> lookForGrabStuff(Player player) {
        return null;
    }

    @Override
    public List<Player> lookForShootPeople(Player player) {
        return null;
    }

    @Override
    public List<WeaponCard> checkReload(Player player) {
        return null;
    }
}