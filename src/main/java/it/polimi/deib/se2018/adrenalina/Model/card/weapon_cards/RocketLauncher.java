package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestRocketLauncher;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseRocketLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class implements the weapon Rocket Laucher.
 *
 * @author giovanni
 */


public class RocketLauncher extends WeaponCard
{

    private boolean[] availableMethod = new boolean[3];


    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public RocketLauncher( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Lanciarazzi";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }



    /**
     * It checks which modes of the weapon can be used
     * @return an array of boolean of which modes are available to the players
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;
        availableMethod[2] = false;

        List<Square> squareList = new ArrayList<>();

        squareList.addAll(MethodsWeapons.squareThatSee(player));
        squareList.remove(player.getSquare());


        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, squareList))
                availableMethod[0] = true;

        if (isLoaded() && player.getAmmoBlue() > 0 && (!checkRocketJumpColors().isEmpty()) && availableMethod[0])
                availableMethod[1] = true;


        if (isLoaded() && player.getAmmoYellow() > 0 && availableMethod[0])
                availableMethod[2] = true;

        return availableMethod;

    }

    /**
     * This method check all the possible square where the owner can move from his original position.
     *
     * @return a list of coordinates of the available squares
     */
    public List<String> allSquaresNoMove ()
    {
        List<Square> squareList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        squareList.addAll(player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2));

        for (Square square : squareList)
            stringList.add(square.toStringCoordinates());

        return stringList;

    }


    /**
     * This method implements the basic mode of the weapon.
     *
     * @param colorPlayerTarget is the target of the basic mode
     * @param squareCoordinatesAsStringPlayertoMove are the coordinates as string for the square where the player decided to move
     * @param squareCoordinatesAsStringTargetToMove are the coordinates as string of the square where the target has to be moved
     * @param withFragWar is a boolean to indicate if use or not the additional "with fragmenting grenade mode"
     */
    public void basicMode (ColorId colorPlayerTarget , String squareCoordinatesAsStringPlayertoMove, String squareCoordinatesAsStringTargetToMove, boolean withFragWar)
    {
        boolean rememberToMoveTarget = false;

        int xPlayer = 0;
        int yPlayer = 0;

        int xTarget = 0;
        int yTarget = 0;


        // if the strings are not null it means i have to move the player and/or the target. I extract the coordinates to use later
        if (squareCoordinatesAsStringPlayertoMove != null)
        {
             xPlayer = MethodsWeapons.getXFromString(squareCoordinatesAsStringPlayertoMove);
             yPlayer = MethodsWeapons.getYFromString(squareCoordinatesAsStringPlayertoMove);
        }

        if (squareCoordinatesAsStringTargetToMove != null)
        {
            xTarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
            yTarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
        }


        //I will damage the target
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayerTarget.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),2);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0),2);

        // I set a flag to remeber to move the target later. I'' doing this beacuse the fragmenting grenade will damage all the targets before to move the target of the basic mode
        if (squareCoordinatesAsStringTargetToMove != null)
            rememberToMoveTarget = true;

        //If this flag is true i will damage all the targets in the original square
        if (withFragWar)
            for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
            {
                if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerIterate.getColor().equals(ColorId.PURPLE))
                    doDamage(player.getSquare().getGameBoard().getTermi(),1);
                else
                    doDamage(playerIterate, 1);

                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }

        //i will move the owner of the weapon if he decided to (so the string is not null)
        if (squareCoordinatesAsStringPlayertoMove != null)
        {
            moveTarget(this.player, xPlayer, yPlayer);
            player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }

        //if the flag is true i will finally move the target to another square at distance 1 that the shooter chose
        if (rememberToMoveTarget)
        {
            if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayerTarget.equals(ColorId.PURPLE))
                moveTarget(player.getSquare().getGameBoard().getTermi(), xTarget, yTarget);
            else
                moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0), xTarget, yTarget);
        }

        this.isLoaded = false;

    }




    public HashMap<ColorId, List<String>> checkSquareToMoveBasicMode ()
    {
        HashMap<ColorId, List<String>> hashMapToReturn = new HashMap<>();

        List<String> stringList;
        List<Square> squareList;

        for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer())
        {
            if (playerIterate.getSquare() != null)
            {
                stringList = new ArrayList<>();
                squareList = new ArrayList<>();

                squareList.addAll(player.getSquare().getGameBoard().getArena().squareReachableNoWall(playerIterate.getSquare().getX(), playerIterate.getSquare().getY(), 1));

                for (Square squareIterate : squareList) {
                    stringList.add(squareIterate.toStringCoordinates());
                }

                hashMapToReturn.put(playerIterate.getColor(), stringList);
            }
        }

        return hashMapToReturn;

    }

    /**
     * This method will check if the shooter can use the tagback grenade (there have to be at least 2 targets in asquare, 1 for the basic mode and 1 additional to activate the tagback)
     * @return a boolean flag
     */
    public boolean checkWithFragmentingWarhead ()
    {
        List<Player> players = new ArrayList<>();

        for (Player playerIterate : player.playerThatSee(player.getSquare().getGameBoard()))
        {
            if (playerIterate.getSquare() != player.getSquare())
                players.add(playerIterate);
        }

        for (Player playerIterate : players)
        {
            if (playerIterate.getSquare().getPlayerList().size()>1)
                return true;
        }

        return false;
    }

    /**
     * This method will check if the tagback mode is available after the target moves with the rocket jump.
     *
     * @return a boolean flag.
     */
    public boolean checkWithFragmentingWarheadRocketJump ()
    {
        Map<Square, List<Player>> squarePlayerListMap = checkRocketJump();
        List<Player> playerList;

        for (Square squareIterate : squarePlayerListMap.keySet())
        {

            for (Player playerIterate : squarePlayerListMap.get(squareIterate))
            {
                playerList = new ArrayList<>();

                playerList.addAll(playerIterate.getSquare().getPlayerList());
                playerList.remove(player);

                if (playerList.size()>1)
                    return true;

            }
        }

        return false;
    }


    /**
     * This method checks the targets if the player choose to move with the rocket jumo mode.
     *
     * @return an hashmap of possible squares to move as keys and a list of targets to shoot for the basic mode from that square
     */
    private Map<Square, List<Player>> checkRocketJump()
    {
        Player dummie = new Player(null,null,null,false);

        Map<Square, List<Player>> squareColorIdListHashMap = new HashMap<>();
        List<Player> playersSeen = new ArrayList<>();
        List<Player> playersSeenCopy = new ArrayList<>();

        List<Square> targetSquares = new ArrayList<>();
        targetSquares.addAll(player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2)); //Obtain all players that can be targets
        targetSquares.remove(player.getSquare());

        //i will set a dummie player in all the possible squares at distance 2 and i will check if there is still at least 1 target for the basic mode
        for(Square squareIterate : targetSquares)
        {
            playersSeen = new ArrayList<>();
            playersSeenCopy = new ArrayList<>();
            dummie.setSquare(squareIterate);

            playersSeen.addAll(dummie.playerThatSee(player.getSquare().getGameBoard()));
            playersSeen.remove(dummie);
            playersSeen.remove(player);

            playersSeenCopy.addAll(playersSeen);

            for (Player playerIterate : playersSeen)
            {
                if (playerIterate.getSquare() == dummie.getSquare())
                    playersSeenCopy.remove(playerIterate);
            }

            if (!playersSeenCopy.isEmpty())
            {
                squareColorIdListHashMap.put(squareIterate, playersSeenCopy);
            }
        }

        return squareColorIdListHashMap; //Returns all targets

    }

    /**
     * This method will trasform the square and player of the rocket jumop hashmap into strings and colorids.
     *
     * @return an hashmap of strings as keys and colorids as target for this mode
     */
    public HashMap<String, List<ColorId>> checkRocketJumpColors ()
    {
        Map<Square, List<Player>> mapRocket = checkRocketJump();
        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();
        List<ColorId> colorIdList ;

        for (Square squareIterate : mapRocket.keySet())
        {
            colorIdList = new ArrayList<>();

            for (Player playerIterate : mapRocket.get(squareIterate))
            {
                colorIdList.add(playerIterate.getColor());
            }

            hashMapToReturn.put(squareIterate.toStringCoordinates(), colorIdList);
        }

        return hashMapToReturn;

    }

    /**
     * This method checks all the targets available for the basic mode.
     *
     * @return a list of colorId of targets
     */
    public List<ColorId> checkPlayersBasicMode ()
    {
        List<ColorId> players = new ArrayList<>();

        for (Player playerIterate : player.playerThatSee(player.getSquare().getGameBoard()))
        {
            if (playerIterate.getSquare() != player.getSquare())
                players.add(playerIterate.getColor());
        }
        return players;
    }


    /**
     * This method extracts the targets for the modes of the weapon.
     *
     * @param responseMessage is the response generated for the weapon.
     */
    @Override
    public void useWeapon(ResponseInput responseMessage)
    {
        basicMode(((ResponseRocketLauncher) responseMessage).getTargetPlayerBasicMode(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringPlayerToMove(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringTargetToMove(), ((ResponseRocketLauncher) responseMessage).isWithFragWarhead() ) ;
    }


    /**
     * This method will create a request message for this weapon.
     *
     * @return the new request
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestRocketLauncher(checkAvailableMode(), checkPlayersBasicMode(), checkRocketJumpColors(), checkSquareToMoveBasicMode(), allSquaresNoMove());

    }





}