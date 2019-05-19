package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.*;
import java.util.stream.Collectors;

public class PowerGlow extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    /**
     * Create the card PowerGlow
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public PowerGlow( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "PowerGlow";
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
        redAmmoCost = 0;
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
        boolean checkmode2 = false; //to check the second mode

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)//If the first mode can be used
            avaiableMethod[0] = true;

        if (isLoaded() && !checkInRocketFistMode().keySet().isEmpty() && player.getAmmoBlue() >= 1)//If the second mode can be used
            avaiableMethod[1] = true;


        return avaiableMethod;
    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the shotgun in basic mode(list)
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Set<Player> target = MethodsWeapons.playersReachable(player.getSquare(),1); //Obtain all players reachable to distance 1

        target.remove(player);//Remove the player that has this card

        return new ArrayList<>(target);//Returns all targets
    }


    /**
     * It uses the basic mode of the PowerGlow
     * Do the damage and marks at a player and the player that has the powerglow is moved in same square
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,1);//Do one damage
        markTarget(player,2);//Do two marks
        moveTarget(this.player,player.getSquare().getX(),player.getSquare().getY()); //Move the player with PowerGlow in square
        //Where the player hit him is located

        isLoaded = false;
    }
    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return Map with key a direction and value a list of lists of players that can be affected with PowerGlow in
     * this direction
     * Each list contain the players in one of two square in one direction
     * The player with the PowerGlow will have to choice a player for each list to use in the mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public Map<String,List<List<ColorId>>> checkInRocketFistMode() throws IllegalStateException
    {

        List<Player> playerList; //list of support to make the value of hash map
        Map<String,List<List<ColorId>>> result = new HashMap<>(); //Map to return

        //Checking in rocket fist mode
        //Obtain all square to distance 2
        Set<Square> squareList = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);

        //Filter the square that are at north with all possible targets


        List<List<ColorId>> squaresN = squareList
                .stream()
                .filter(square -> MethodsWeapons.checkSquareNorth(player.getSquare(), square.getX(), square.getY()))
                .sorted((o1, o2) -> o1.getY() - o2.getY())
                .map(Square::getPlayerListColor)
                .filter(players -> !players.isEmpty())
                .collect(Collectors.toList());


        if (squaresN.size()==2)//If there are two target
            result.putIfAbsent("Nord",squaresN);//Add at map


        //Filter the square that are at South with all possible targets
        List<List<ColorId>> squaresS = squareList
                .stream()
                .filter(square -> MethodsWeapons.checkSquareSouth(player.getSquare(), square.getX(), square.getY()))
                .sorted((o1, o2) -> o2.getY() - o1.getY())
                .map(Square::getPlayerListColor)
                .filter(players -> !players.isEmpty())
                .collect(Collectors.toList());

        if (squaresS.size() == 2)//If there are two target
            result.putIfAbsent("Sud",squaresS);//Add at map

        //Filter the square that are at East with all possible targets
        List<List<ColorId>> squaresE = squareList
                .stream()
                .filter(square -> MethodsWeapons.checkSquareEast(player.getSquare(), square.getX(), square.getY()))
                .sorted((o1, o2) -> o1.getX() - o2.getX())
                .map(Square::getPlayerListColor)
                .filter(players -> !players.isEmpty())
                .collect(Collectors.toList());

        if (squaresE.size() == 2)//If there are two target
            result.putIfAbsent("Est",squaresE);//Add at map

        //Filter the square that are at West with all possible targets
        List<List<ColorId>> squaresW = squareList
                .stream()
                .filter(square -> MethodsWeapons.checkSquareWest(player.getSquare(), square.getX(), square.getY()))
                .sorted((o1, o2) -> o2.getX() - o1.getX())
                .map(Square::getPlayerListColor)
                .filter(players -> !players.isEmpty())
                .collect(Collectors.toList());

        if (squaresW.size()==2) //If there are two target
            result.putIfAbsent("Ovest",squaresW);//Add at map


        return result;
    }

    /**
     * It uses the alternative mode of the Powerglow
     * @param player1 player affected for first with the powerglow
     * @param player2 player affected for last with the powerglow
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inRocketFistMode(Player player1,Player player2) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player1,2);//Do the damage points
        doDamage(player2,2);
        moveTarget(player,player2.getSquare().getX(),player2.getSquare().getY());//The player that has the powerglow is moved in the square of player2

        player.setAmmoBlue(player.getAmmoBlue() - 1); //Use the ammo necessary

        isLoaded = false;//The weapon is not loaded now
    }
}

