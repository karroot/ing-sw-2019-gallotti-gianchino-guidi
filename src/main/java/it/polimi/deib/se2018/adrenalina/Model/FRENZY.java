package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;
import java.util.Set;

public class FRENZY implements StatePlayer
{

    public FRENZY()
    {

    }

    //qui va implementato un po' diverso. Bisognerebbe prima far scegliere se si vuole muovere per sparare o raccogliere e poi lanciare il metodo opportuno.
    // Forse avrebbe più senso implementarlo a parte

    //Se primo: muovi 2 ricarica e spara OPPURE muove 3 e raccogli

    //se non primo: muovi 1, ricarica e spara OPPURE muovi 4 OPPURE muovi 2 e raccogli


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