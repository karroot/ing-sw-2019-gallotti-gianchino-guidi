package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class VortexCannon extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];


    /**
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public VortexCannon( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Vortex Cannon";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }


    /**
     * This method checks which modes of the weapon can be used by the player that owns this weapon
     *
     * @return array of booleans of size 2. The first boolean represents the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong to any player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception

        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;


        //scelgo uno square che posso vedere ma non è il mio. Risucchio 1 player a distanza max 1 dallo square nello square e faccio due danni

        //blackhole: in un raggio di distanza 1 dallo square risucchio fino ad altri 2 player nello square e faccio 1 danno a ciascuno

        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, (List<Square>) MethodsWeapons.squareThatSee(player)))
        {
                availableMethod[0] = true;
        }

        if (isLoaded && availableMethod[0] && player.getAmmoRed() >= 1)
        {
            availableMethod[1] = true;
            //todo controllo aggiuntivo player??
        }

        return availableMethod;

    }

    /**
     * Return a list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
//square o players?
    private HashMap<Square, ArrayList<Player>> checkBasicModeFull() throws IllegalStateException
    {
            if (!checkAvailableMode()[0]) //check mode
                throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

            HashMap<Square, ArrayList<Player>> hashMapreturn = new HashMap<Square, ArrayList<Player>>();

            Player playerTemp = new Player(null,null, null, true);

            List<Square> squareTarget = new ArrayList<>();
            List<Player> playersTarget;
            squareTarget = (ArrayList) MethodsWeapons.squareThatSee(player);
            squareTarget.remove(player.getSquare()); //crate squarelist of possible vortex locations

            for (Square squareIterate: squareTarget)
            {
                playersTarget = new ArrayList<>();
                playerTemp.setSquare(squareIterate);
                for (Square squareIterate2: playerTemp.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1)) //all the square at distance 1 from the possible location of the vortex
                {
                  playersTarget.addAll(squareIterate2.getPlayerList());

                }
                if (!playersTarget.isEmpty())
                    hashMapreturn.put(squareIterate, (ArrayList) playersTarget);
            }

        return hashMapreturn;

    }

    private List<String> checkBasicModeSquares ()
    {
        HashMap<Square, ArrayList<Player>> squarePlayersHashMap = checkBasicModeFull();

        List<String> squareListCoordinatesAsString = new ArrayList<>();

        for (Square squareIterate : squarePlayersHashMap.keySet())
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());

        return squareListCoordinatesAsString;

    }


    private List<ColorId> checkBasicModePlayers (String squareTargetCoordinatesAsString)
    {
        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);

        Square square = null;
        List<ColorId> colorIdList = new ArrayList<>();

        try {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

        HashMap<Square, ArrayList<Player>> squarePlayersHashMap = checkBasicModeFull();

        for (Player playerIterate : squarePlayersHashMap.get(square))
            colorIdList.add(playerIterate.getColor());

        return colorIdList;

    }

    public HashMap<String, List<ColorId>> checkBasicMode ()
    {
        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();

        List<String> stringListKeys = checkBasicModeSquares();
        List<ColorId> colorIdListAttributes = new ArrayList<>();

        for (String stringIterate : stringListKeys)
        {
            colorIdListAttributes.clear();
            colorIdListAttributes = checkBasicModePlayers(stringIterate);
            hashMapToReturn.put(stringIterate, colorIdListAttributes);
        }

        return hashMapToReturn;

    }

    public void basicMode (ColorId colorPlayer, String squareToMoveCoordinatesAsString)
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        int x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsString);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),2);

        MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);

        isLoaded = false;


    }


    //todo
    public List<ColorId> checkWithBlackHoleMode(ColorId colorPlayerAlreadySelected, String squareTargetCoordinatesAsString) throws IllegalStateException
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità black hole dell'arma: "+name+" non eseguibile");

        List<ColorId> colorIdList = checkBasicModePlayers(squareTargetCoordinatesAsString);
        colorIdList.remove(colorPlayerAlreadySelected);
        if (!colorIdList.isEmpty())
            return colorIdList;
        else return null;
    }

    public void blackHoleMode (ColorId playerTarget1, ColorId playerTarget2, String vortexSquareAsString)
    {
        int x = MethodsWeapons.getXFromString(vortexSquareAsString);
        int y = MethodsWeapons.getYFromString(vortexSquareAsString);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget1)).collect(Collectors.toList()).get(0),1);
        MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget1)).collect(Collectors.toList()).get(0), x, y);

        if (playerTarget2 != null)
        {
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget2)).collect(Collectors.toList()).get(0),1);
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget2)).collect(Collectors.toList()).get(0), x, y);
        }

        player.setAmmoRed(player.getAmmoRed() - 1);
        isLoaded = false;

    }

    public void useWeapon(ResponseInput responseMessage)
    {
        if (((ResponseVortexCannon) responseMessage).isMode())

            blackHoleMode(((ResponseVortexCannon) responseMessage).getTarget1BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTarget2BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
        else
            basicMode(((ResponseVortexCannon) responseMessage).getTargetPlayerBasicMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
    }

    @Override
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])

            return new RequestShockwave(checkAvailableMode(),checkBasicMode());

        else if(checkAvailableMode()[0] && !checkAvailableMode()[1])

            return new RequestShockwave(checkAvailableMode(),checkBasicMode());

        else
            return new RequestShockwave(checkAvailableMode(),new HashMap<>());
    }
}

