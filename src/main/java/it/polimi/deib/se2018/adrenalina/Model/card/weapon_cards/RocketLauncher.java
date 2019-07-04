package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;


/**
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
        {
                availableMethod[0] = true;
        }


        if (isLoaded() && player.getAmmoBlue() > 0 && (!checkRocketJumpColors().isEmpty()))
        {
            if (availableMethod[0])
                availableMethod[1] = true;
        }

        if (isLoaded() && player.getAmmoYellow() > 0)
        {
            if (availableMethod[0])
                availableMethod[2] = true;
        }

        return availableMethod;

    }

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
     *
     * @param colorPlayerTarget
     * @param squareCoordinatesAsStringPlayertoMove
     * @param squareCoordinatesAsStringTargetToMove
     * @throws IllegalStateException
     */
    public void basicMode (ColorId colorPlayerTarget , String squareCoordinatesAsStringPlayertoMove, String squareCoordinatesAsStringTargetToMove, boolean withFragWar) throws IllegalStateException
    {
        boolean rememberToMoveTarget = false;

        int xplayer = 0;
        int yplayer = 0;

        int xtarget = 0;
        int ytarget = 0;


        if (squareCoordinatesAsStringPlayertoMove != null)
        {
             xplayer = MethodsWeapons.getXFromString(squareCoordinatesAsStringPlayertoMove);
             yplayer = MethodsWeapons.getYFromString(squareCoordinatesAsStringPlayertoMove);
        }

        if (squareCoordinatesAsStringTargetToMove != null)
        {
            xtarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
            ytarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
        }

        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayerTarget.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),2);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0),2);

        if (squareCoordinatesAsStringTargetToMove != null)
            rememberToMoveTarget = true;

        if (withFragWar)
            for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
            {
                if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerIterate.getColor().equals(ColorId.PURPLE))
                    doDamage(player.getSquare().getGameBoard().getTermi(),1);
                else
                    doDamage(playerIterate, 1);

                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }

        if (squareCoordinatesAsStringPlayertoMove != null)
        {
            moveTarget(this.player, xplayer, yplayer);
            player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }

        if (rememberToMoveTarget)
        {
            if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayerTarget.equals(ColorId.PURPLE))
                moveTarget(player.getSquare().getGameBoard().getTermi(), xtarget, ytarget);
            else
                moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0), xtarget, ytarget);
        }

        this.isLoaded = false;

    }




    public HashMap<ColorId, List<String>> checkSquareToMoveBasicMode ()
    {
        HashMap<ColorId, List<String>> hashMapToReturn = new HashMap<>();

        List<String> stringList = new ArrayList<>();
        List<Square> squareList = new ArrayList<>();

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
     *
     * @return
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

    public boolean checkWithFragmentingWarheadRocketJump ()
    {
        Map<Square, List<Player>> squarePlayerListMap = checkRocketJump();
        List<Player> playerList = new ArrayList<>();

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
     *
     * @return
     * @throws IllegalStateException
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

    public HashMap<String, List<ColorId>> checkRocketJumpColors ()
    {
        Map<Square, List<Player>> mapRocket = checkRocketJump();
        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();
        List<ColorId> colorIdList = new ArrayList<>();

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
     *
     * @return
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
     *
     * @param
     */
    @Override
    public void useWeapon(ResponseInput responseMessage)
    {
        basicMode(((ResponseRocketLauncher) responseMessage).getTargetPlayerBasicMode(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringPlayerToMove(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringTargetToMove(), ((ResponseRocketLauncher) responseMessage).isWithFragWarhead() ) ;
    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestRocketLauncher(checkAvailableMode(), checkPlayersBasicMode(), checkRocketJumpColors(), checkSquareToMoveBasicMode(), allSquaresNoMove());

    }





}