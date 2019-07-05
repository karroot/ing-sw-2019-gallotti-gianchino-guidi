package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestGrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseGrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This class implements the weapon Grenade Launcher.
 *
 * @author gioguidi
 */


public class GrenadeLauncher extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];


    /**
     * It is the public constructor for the class.
     *
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public GrenadeLauncher (Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Lanciagranate";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    /**
     * It checks which modes of the weapon can be used.
     *
     * @return an array of boolean of which modes are available to the players
     */
    public boolean[] checkAvailableMode()
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong to any player, there is a runtime exception

        int i=0;

        availableMethod[i] = false; //I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && !player.playerThatSee(player.getSquare().getGameBoard()).isEmpty())
        {
                availableMethod[0] = true;
        }

        if (availableMethod[0] && player.getAmmoRed() > 0)
        {
                availableMethod[1] = true;
        }

        return availableMethod;

    }

    /**
     * This method checks the possible targets for the basic mode of this weapon.
     *
     * @return a list of players as possible targets
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    private HashMap<Player, Square> checkBasicModePlayers() throws IllegalStateException
    {

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playersTarget.remove(player); //Remove the player that has this card

        HashMap<Player, Square> hashMapToReturn = new HashMap<>();

        for (Player playerIterate : playersTarget)
        {
            hashMapToReturn.put(playerIterate, playerIterate.getSquare());
        }

        return hashMapToReturn; //Returns all targets
    }

    /**
     * This method checks the possible targets for the basic mode of this weapon.
     *
     * @return a list of ColorId of possible targets
     */
    public  HashMap<ColorId, String>checkBasicMode()
    {

        HashMap<ColorId, String> colorIdStringHashMap = new HashMap<>();
        HashMap<Player, Square> playerSquareHashMap;
        playerSquareHashMap = checkBasicModePlayers();

        for (Player playerIterate : playerSquareHashMap.keySet())
        {
            colorIdStringHashMap.put(playerIterate.getColor(), playerIterate.getSquare().toStringCoordinates());
        }

        return colorIdStringHashMap; //Returns all targets

    }

    /**
     * Checks the square target for the basic mode.
     *
     * @return an hashmap of ColorId and square as strings
     */
    public HashMap<ColorId, List<String>> checkBasicModeSquares ()
    {
        HashMap<ColorId, List<String>> hashMapToReturn = new HashMap<>();
        List<ColorId> colorIdList = new ArrayList<>();
        colorIdList.addAll(checkBasicMode().keySet());
        List<String> squareAsStringCoordinatesList = new ArrayList<>();
        Set<Square> squareSet = new HashSet<>();


        for (ColorId colorIdIterate : colorIdList)
        {
            squareSet = new HashSet<>();
            squareAsStringCoordinatesList = new ArrayList<>();

            //This lambda will get all the squares at distance 1 for the player, intended as colorId
            squareSet = player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorIdIterate)).collect(Collectors.toList()).get(0).getSquare().getGameBoard().getArena()
                    .squareReachableNoWall(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorIdIterate)).collect(Collectors.toList()).get(0).getSquare().getX(),
                                            player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorIdIterate)).collect(Collectors.toList()).get(0).getSquare().getY(),
                                        1);

            for (Square squareIterate : squareSet)
            {
                squareAsStringCoordinatesList.add(squareIterate.toStringCoordinates());
            }


            hashMapToReturn.put(colorIdIterate, squareAsStringCoordinatesList); //prepare the hashmap to return

        }

        return hashMapToReturn;
    }


    /**
     * This method implements the basic mode of the weapon.
     *
     * @param colorPlayer is the target player
     * @param squareToMoveCoordinatesAsString is the square where the player has to be moved
     */
    public void basicMode(ColorId colorPlayer, String squareToMoveCoordinatesAsString)
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        int x = 0;
        int y = 0;

        if (squareToMoveCoordinatesAsString != null)
        {
             x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsString);
             y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsString);
        }

        //do 1 damage to the target
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),1);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        //if the target has to be moved, it will
        if (squareToMoveCoordinatesAsString != null)
        {
            if (this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
                moveTarget(player.getSquare().getGameBoard().getTermi(), x, y);
            else
                moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);
        }

        isLoaded = false;
    }

    /**
     * Checks the square target for the extra grenade.
     *
     * @return a list of square coordinate of possible targets
     */
    public List<String> checkExtraGrenade ()
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Granata extra dell'arma "+name+" non eseguibile.");

        List<String> squareListCoordinatesAsString = new ArrayList<>();


        Set<Square> squaresTarget = MethodsWeapons.squareThatSee(player); //Obtain all squares that player can see

        for (Square squareIterate : squaresTarget)
        {
            if (!squareIterate.getPlayerList().isEmpty())
            {
                if (squareIterate.getPlayerList().contains(player))
                {
                    if (squareIterate.getPlayerList().size() >= 2)
                        squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());
                } else {
                    squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());
                }
            }
        }

        return squareListCoordinatesAsString;//Returns all targets
    }

    /**
     * This method checks all the squares that the player who call the method sees.
     *
     * @return a list of string of coordinates of all the squares that the player sees.
     */
    public List<String> checkAllSquaresISee ()
    {
        List<String> squareListCoordinatesAsString = new ArrayList<>();


        Set<Square> squaresTarget = MethodsWeapons.squareThatSee(player);


        for (Square squareIterate : squaresTarget)
        {
                squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());
        }

        return squareListCoordinatesAsString;

    }

    /**
     * It implements the extra grenade effect for this weapon.
     *
     * @param squareTargetCoordinatesAsString is the square target where I will deal 1 damage
     *
     * @throws Exception if the square is not valid
     */
    public void extraGrenade (String squareTargetCoordinatesAsString) throws Exception
    {

        Square square = null;

        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);


        square = player.getSquare().getGameBoard().getArena().getSquare(x, y);


        if (square != null) {
            for (Player playerIterate : square.getPlayerList())
            {
                if (!playerIterate.equals(player)) {
                    if (this.player.getSquare().getGameBoard().isTerminatorMode() && playerIterate.getColor().equals(ColorId.PURPLE))
                        doDamage(player.getSquare().getGameBoard().getTermi(), 1);
                    else
                        doDamage(playerIterate, 1);//Do one damage
                }
            }
        }

        player.setAmmoRed(player.getAmmoRed() - 1);
        isLoaded = false;

    }

    /**
     * This method extracts the targets for the modes of the weapon.
     *
     * @param responseInput is the response generated for the weapon.
     *
     */


    /**
     * This method extracts the targets for the modes of the weapon.
     *
     * @param responseInput is the response generated for the weapon.
     *
     * @throws Exception if the square is not valid
     */
    @Override
    public void useWeapon(ResponseInput responseInput) throws Exception
    {
        basicMode(((ResponseGrenadeLauncher) responseInput).getTargetBasicMode(), ((ResponseGrenadeLauncher) responseInput).getTargetSquareToMoveBasicModeAsString());
        if (((ResponseGrenadeLauncher) responseInput).isExtraEffect())
            extraGrenade(((ResponseGrenadeLauncher) responseInput).getTargetSquareExtraGrenadeAsString());

    }


    /**
     * This method will create a request message for this weapon.
     *
     * @return the new request
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestGrenadeLauncher(checkAvailableMode(),checkBasicMode(), checkExtraGrenade(), checkBasicModeSquares(), checkAllSquaresISee());
    }


}
