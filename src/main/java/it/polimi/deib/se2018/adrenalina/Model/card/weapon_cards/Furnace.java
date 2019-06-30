package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */

public class Furnace extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];


    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public Furnace( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Vulcanizzatore";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }


    /**
     * It checks which modes of the weapon can be used
     * @return an array of boolean of which modes are available to the players
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode () throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded()) {
            List<Room> roomList = new ArrayList<>();

            roomList = MethodsWeapons.roomsThatIsee(player);
            roomList.remove(player.getSquare().getRoom()); //remove myself from the playerList
            for (Room roomIterate : roomList) {
                if (!roomIterate.getPlayerRoomList().isEmpty()) {
                    availableMethod[0] = true;
                    break;
                }

            }
        }

        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player)) {
            availableMethod[1] = true;
        }

        return availableMethod;
    }


    /**
     * This method checks the target for the basic mode.
     * @return a list of the possible rooms to target
     */
    public List<ColorRoom> checkBasicMode ()
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<Room, ArrayList<Player>> hashRoomPlayer = new HashMap<Room, ArrayList<Player>>();
        List<ColorRoom> colorRoomList = new ArrayList<>();

        List<Room> roomList;

        roomList = MethodsWeapons.roomsThatIsee(player);
        roomList.remove(player.getSquare().getRoom()); //remove my room from the roomList
        for (Room roomIterate : roomList) {
            if (!roomIterate.getPlayerRoomList().isEmpty())
                hashRoomPlayer.put(roomIterate, (ArrayList) roomIterate.getPlayerRoomList());
        }

        for (Room roomIterate : hashRoomPlayer.keySet())
            colorRoomList.add(roomIterate.getColor());

        return colorRoomList;

    }

    /**
     *  This method is for the basic mode of the weapon.
     * @param roomColor is the color of the room to fire
     */
    public void basicMode (ColorRoom roomColor)
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        Room room =  player.getSquare().getGameBoard().getRoomList().stream().filter(room1 -> room1.getColor().equals(roomColor)).collect(Collectors.toList()).get(0);

        for (Player playerIterate : room.getPlayerRoomList())
        {
            doDamage(playerIterate, 1);
        }

        isLoaded = false;

    }


    /**
     * Checks the target for the second mode of the weapon
     * @return a list of String of coordinate of the possible squares to target
     */
    public List<String> checkInCozyFireMode () {

        List<String> squareListAsStringToReturn = new ArrayList<>();
        List<Square> squareList = new ArrayList<>();

        squareList.addAll(player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1));
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList)
        {
            if (!squareIterate.getPlayerList().isEmpty())
                squareListAsStringToReturn.add(squareIterate.toStringCoordinates());
        }


        return squareListAsStringToReturn;

    }

    /**
     * This method implements the alternative mode of the weapon.
     * @param squareTargetCoordinatesAsString is the target square
     */
    public void inCozyFireMode (String squareTargetCoordinatesAsString)
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Modalità fuoco confortevole dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);

        Square square = null;

        try {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

        if (square != null)
        {
            for (Player playerIterate : square.getPlayerList())
               {
                   doDamage(playerIterate, 1);
                   markTarget(playerIterate, 1);
               }
        }
        else
        {
            throw  new IllegalStateException();
        }

        isLoaded = false;
    }


    /**
     *
     * @param responseInput
     */
    @Override
    public void useWeapon(ResponseInput responseInput)
    {
        if (((ResponseFurnace) responseInput).isMode())
        {
            inCozyFireMode(((ResponseFurnace) responseInput).getTargetAlternativeMode());
            return;
        }

        basicMode(((ResponseFurnace) responseInput).getTargetBasicMode());

    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestFurnace(checkAvailableMode(),checkBasicMode(), checkInCozyFireMode());
    }
}