package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class LockRifle extends WeaponCard implements DoDamage, MarkTarget
{
    private boolean[] avaiableMethod = new boolean[2];

    public LockRifle(Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;
        avaiableMethod[1] = false;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

    @Override
    public void doDamage(Player player, int quantity) {

    }

    public void markTarget()
    {

    }

}