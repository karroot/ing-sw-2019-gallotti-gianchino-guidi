package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class THOR extends WeaponCard implements DoDamage
{
    private boolean[] avaiableMethod = new boolean[3];

    public THOR( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;
        avaiableMethod[1] = false;
        avaiableMethod[2] = false;

        if (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>1 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[2] = true;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;



        return avaiableMethod;

    }

    @Override
    public void doDamage(Player player, int quantity) {

    }
}