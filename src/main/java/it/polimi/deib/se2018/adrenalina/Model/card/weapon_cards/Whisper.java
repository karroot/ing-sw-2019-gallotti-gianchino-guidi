package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;


public class Whisper extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[1];

    public Whisper( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;




        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0 &&  MethodsWeapons.playersReachable(player.getSquare(),1).size() > 2)
            avaiableMethod[0] = true;



        return avaiableMethod;

    }


}