package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Hellion extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    /**
     * This is the constructor for the card Hellion
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public Hellion( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Hellion";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        avaiableMethod[0] = false;//I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded())
        {
            Set<Player> playerSet;
            playerSet = player.playerThatSee(player.getSquare().getGameBoard());

            for (Player playerIterate : playerSet)
            {
                if (playerIterate.getSquare() == player.getSquare()) //check is there is a player i
                {
                   playerSet.remove(playerIterate);
                }
            }

            if (!playerSet.isEmpty())
                avaiableMethod[0] = true;

        }


        if (isLoaded() && player.getAmmoRed() > 0)
        {
            if (avaiableMethod[0])
                avaiableMethod[1] = true;
        }

        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        for (Player playerIterate : playersTarget)
        {
            if (playerIterate.getSquare() == player.getSquare()) //check is there is a player i
            {
                playersTarget.remove(playerIterate);
            }
        }

        return new ArrayList<>(playersTarget);//Returns all targets
    }

    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,1);//Do one damage

        for (Player playerIterate : player.getSquare().getPlayerList())
        {
            markTarget(playerIterate,1);//Do two marks
        }

        isLoaded = false;
    }

    public List<Player> checkNanoTracerMode () throws IllegalStateException
    {
        return checkBasicMode();
    }

    public void nanoTracerMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        doDamage(player,1);//Do one damage

        for (Player playerIterate : player.getSquare().getPlayerList())
        {
            markTarget(playerIterate,2);//Do two marks
        }

        player.setAmmoRed(player.getAmmoRed()-1);

        isLoaded = false;
    }
}