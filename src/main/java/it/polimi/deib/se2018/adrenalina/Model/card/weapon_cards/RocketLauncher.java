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

    private boolean[] availableMethod = new boolean[4];
    private Player dummie = new Player(ColorId.BLUE,"a","a",false);

    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public RocketLauncher( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Rocket Launcher";
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
        availableMethod[3] = false;

        List<Square> squareList = new ArrayList<>();

        squareList.addAll(MethodsWeapons.squareThatSee(player));


        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, squareList))
        {
                availableMethod[0] = true;
        }


        if (isLoaded() && availableMethod[0] && player.getAmmoBlue() > 0 && checkPhaseGlide().size()>1)
        {
            availableMethod[1] = true;
        }

        if (isLoaded() && availableMethod[0] && player.getAmmoYellow() > 0)
        {
            availableMethod[2] = true;
        }

        return availableMethod;

    }

    /**
     * It checks the target for the basic mode
     *
     * @return a list of ColorId of possible targets
     * @throws IllegalStateException if the mode is not available
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen
        List<ColorId> colorIdList = new ArrayList<>();

        playersTarget.remove(player);

        for (Player playerIterate : playersTarget)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;//Returns all targets
    }

    /**
     *
     * @param colorPlayerTarget
     * @param orderEffect
     * @param squareCoordinatesAsStringPlayertoMove
     * @param squareCoordinatesAsStringTargetToMove
     * @throws IllegalStateException
     */
    public void basicMode (ColorId colorPlayerTarget , String[] orderEffect, String squareCoordinatesAsStringPlayertoMove, String squareCoordinatesAsStringTargetToMove) throws IllegalStateException {
        if (!checkAvailableMode()[0]) //check mode
            throw new IllegalStateException("Modalità xxx dell'arma: " + name + " non eseguibile");

        int xplayer = MethodsWeapons.getXFromString(squareCoordinatesAsStringPlayertoMove);
        int yplayer = MethodsWeapons.getYFromString(squareCoordinatesAsStringPlayertoMove);
        int xtarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
        int ytarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);


        int i = 0;
        boolean[] booleans = checkAvailableMode();
        boolean rememberToMoveTarget = false;
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvailableMode()[0])
                    throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0),2);
                rememberToMoveTarget = true;
            }
            if (orderEffect[i].equals("with rocket jump:") && booleans[1]) {
                if (!checkAvailableMode()[2])
                    throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

                moveTarget(this.player, xplayer, yplayer);
                player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
            if (orderEffect[i].equals("with fragmenting warhead") && booleans[2])
            {
                if (!checkAvailableMode()[1])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
                for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
                {
                    doDamage(playerIterate,1);
                }
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }
            i++;

            if (rememberToMoveTarget)
                moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0), xtarget, ytarget);
        }

        this.isLoaded = false;
    }


    /**
     *
     * @return
     */
    public List<ColorId> checkPlayersWithRocketJump ()
    {
        Set<Player> playerSet = checkPhaseGlide();
        List<ColorId> colorIdList = new ArrayList<>();
        for (Player playerIterate : playerSet)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;
    }


    /**
     *
     * @return
     */
    public List<ColorId> checkWithFragmentingWarhead ()
    {
        List<Player> playersTargetList;
        List<ColorId> colorIdList = new ArrayList<>();
        playersTargetList = player.getSquare().getPlayerList();
        playersTargetList.remove(player);

        for (Player playerIterate : playersTargetList)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;
    }


    /**
     *
     * @return
     * @throws IllegalStateException
     */
    private Set<Player> checkPhaseGlide()
    {
        Set<Player> playerReachable = new HashSet<>();
        Set<Square> target = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets

        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 0)
                playerReachable.addAll(dummie.playerThatSee(dummie.getSquare().getGameBoard()));
        }
        return playerReachable;//Returns all targets

    }

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    public List<String> checkSquaresToMove() throws IllegalStateException
    {

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),2);//Obtain all the reachable square

        return squares.stream().map(Square::toStringCoordinates).collect(Collectors.toList());//Returns squares as a list of string);
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
        basicMode(((ResponseRocketLauncher) responseMessage).getTargetPlayerBasicMode(),((ResponseRocketLauncher) responseMessage).getOrderEffect(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringPlayerToMove(), ((ResponseRocketLauncher) responseMessage).getTargetSquareCoordinatesAsStringTargetToMove() ) ;
    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestRocketLauncher(checkAvailableMode(), checkPlayersBasicMode(), checkWithFragmentingWarhead(), player.getSquare().getX(), player.getSquare().getY(), checkSquaresToMove(), checkPlayersWithRocketJump());

    }





}