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
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public Furnace( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Vulcanizzatore";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }


    /**
     *
     * @return
     * @throws IllegalStateException
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
     *
     * @return
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
     *
     * @param roomColor
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
     *
     * @return
     */
    public List<String> checkInCozyFireMode () {

        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();

        List<String> squareListCoordinatesAsString = new ArrayList<>();

        Set<Square> squareList;
        squareList = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList)
        {
            hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());
        }

        for (Square squareIterate : squareList)
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());


        return squareListCoordinatesAsString;

    }

    /**
     *
     * @param squareTargetCoordinatesAsString
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