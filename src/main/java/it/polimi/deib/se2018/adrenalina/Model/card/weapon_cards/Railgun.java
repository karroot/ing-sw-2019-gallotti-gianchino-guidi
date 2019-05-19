package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.*;
import java.util.stream.Collectors;

public class Railgun extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    /**
     * Create the card Railgun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Railgun( Color color, int weaponID, boolean isLoaded)throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 2;
        blueAmmoCost = 1;
        redAmmoCost = 0;
        this.name = "Railgun";
    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[0] = false; //I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded() && !checkBasicModeOrPiercingMode().keySet().isEmpty())//If the first mode can be used
            avaiableMethod[0] = true;

        if (isLoaded() && !checkBasicModeOrPiercingMode().keySet().isEmpty() && thereAreTwoPlayerInAdirection())//If the second mode can be used
            avaiableMethod[1] = true;

        return avaiableMethod;

    }

    /**
     * Return an hash map with key a cardinal direction and value all player that are in that direction
     * These player are all possible target for the rail gun
     * @return hash map
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Map<String,List<ColorId>> checkBasicModeOrPiercingMode() throws IllegalStateException
    {
        Map<String,List<ColorId>> result = new HashMap<>();

        //For each cardinal direction
        //1 obtain all square in that direction
        //2 obtain all players in that direction
        //3 Add all players at a list
        //4 If there are players add them at hash map with key that represent that cardinal direction

        //Check the cardinal direction North
        List<Player> playersN = new LinkedList<>();

        Set<Square> N = player.getSquare().getGameBoard().getArena().getAllSquareAtNorth(player.getSquare());

        N.stream().map(square -> square.getPlayerList()).forEach(play -> {play.remove(player); playersN.addAll(play);} );

        if(!playersN.isEmpty())
            result.putIfAbsent("Nord",playersN.stream().map(Player::getColor).collect(Collectors.toList()));

        //Check the cardinal direction East
        List<Player> playersE = new LinkedList<>();

        Set<Square> E = player.getSquare().getGameBoard().getArena().getAllSquareAtEast(player.getSquare());

        E.stream().map(square -> square.getPlayerList()).forEach(play -> {play.remove(player); playersE.addAll(play);} );

        if(!playersE.isEmpty())
            result.putIfAbsent("Est",playersE.stream().map(Player::getColor).collect(Collectors.toList()));

        //Check the cardinal direction South
        List<Player> playersS = new LinkedList<>();

        Set<Square> S = player.getSquare().getGameBoard().getArena().getAllSquareAtSouth(player.getSquare());

        S.stream().map(square -> square.getPlayerList()).forEach(play ->{ play.remove(player); playersS.addAll(play);} );

        if(!playersS.isEmpty())
            result.putIfAbsent("Sud",playersS.stream().map(Player::getColor).collect(Collectors.toList()));

        //Check the cardinal direction South
        List<Player> playersW = new LinkedList<>();

        Set<Square> W = player.getSquare().getGameBoard().getArena().getAllSquareAtWest(player.getSquare());

        W.stream().map(square -> square.getPlayerList()).forEach(play -> { play.remove(player); playersW.addAll(play);} );

        if(!playersW.isEmpty())
            result.putIfAbsent("Ovest",playersW.stream().map(Player::getColor).collect(Collectors.toList()));


        return result;//Returns the map with all cardinal direction where there are the targets possible
    }

    /**
     * It uses the basic mode of the Railgun
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,3);//Do one damage


        isLoaded = false;
    }

    /**
     * It uses the alternative mode of the Railgun
     * @param player1 first player affected by weapon
     * @param player2 second player affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inPiercingMode(Player player1, Player player2) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player1,2);
        doDamage(player2,2);

        isLoaded = false;
    }

    //Says if the alternative mode can be used
    private boolean thereAreTwoPlayerInAdirection()
    {
        Map<String,List<ColorId>> temp = checkBasicModeOrPiercingMode();

        List<List<ColorId>> c = temp.values()
                                    .stream()
                                    .filter(players -> players.size() > 1)
                                    .collect(Collectors.toList());
        return  !c.isEmpty(); //if there are at least two player in a cardinal direction return true
    }


}