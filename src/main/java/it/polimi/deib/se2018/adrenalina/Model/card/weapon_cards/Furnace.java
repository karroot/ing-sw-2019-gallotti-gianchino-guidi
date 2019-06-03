package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

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

    private boolean[] avaiableMethod = new boolean[2];



    public Furnace( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Furnace";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {

    }


    //mod base: stanza che posso vedere ma non la mia -> 1 danno a tutti
    //mod cozy fire: 1 quadrato a distanza 1: 1 danno e 1 marchio a tutt

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        avaiableMethod[0] = false;//I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded()) {
            List<Room> roomList = new ArrayList<>();

            roomList = MethodsWeapons.roomsThatIsee(player);
            roomList.remove(player.getSquare().getRoom()); //controllo che non sia la mia
            for (Room roomIterate : roomList) {
                if (!roomIterate.getPlayerRoomList().isEmpty()) {
                    avaiableMethod[0] = true;
                    break;
                }

            }
        }

        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player)) {
            avaiableMethod[1] = true;
        }

        return avaiableMethod;
    }



    public List<ColorRoom> checkBasicMode ()
    {
        HashMap<Room, ArrayList<Player>> hashRoomPlayer = new HashMap<Room, ArrayList<Player>>();
        List<ColorRoom> colorRoomList = new ArrayList<>();

        List<Room> roomList = new ArrayList<>();

        roomList = MethodsWeapons.roomsThatIsee(player);
        roomList.remove(player.getSquare().getRoom()); //controllo che non sia la mia
        for (Room roomIterate : roomList) {
            if (!roomIterate.getPlayerRoomList().isEmpty())
                hashRoomPlayer.put(roomIterate, (ArrayList) roomIterate.getPlayerRoomList());
        }

        for (Room roomIterate : hashRoomPlayer.keySet())
            colorRoomList.add(roomIterate.getColor());

        return colorRoomList;

    }


    public void basicMode (ColorRoom roomColor)
    {
        Room room =  player.getSquare().getGameBoard().getRoomList().stream().filter(room1 -> room1.getColor().equals(roomColor)).collect(Collectors.toList()).get(0);

        for (Player playerIterate : room.getPlayerRoomList())
        {
            doDamage(playerIterate, 1);
        }
        isLoaded = false;

    }



    //stesso discorso: square o players?

    //ritorno lista square coordinate come stringa

    public List<String> checkInCozyFireMode () {

    HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();

    List<String> squareListCoordinatesAsString = new ArrayList<>();

    Set<Square> squareList;
    squareList = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
    squareList.remove(player.getSquare());

    for (Square squareIterate : squareList) {
        hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());
    }

    for (Square squareIterate : squareList)
        squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());


    return squareListCoordinatesAsString;

    }


    public void inCozyFireMode (String squareTargetCoordinatesAsString)
    {
        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);

        Square square = null;

        try {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

        for (Player playerIterate : square.getPlayerList())
           {
               doDamage(playerIterate, 1);
               markTarget(playerIterate, 1);
           }

           isLoaded = false;
    }








}