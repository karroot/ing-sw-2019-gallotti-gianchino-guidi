package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Room;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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



    public HashMap<Room, ArrayList<Player>> checkBasicMode ()
    {
        HashMap<Room, ArrayList<Player>> hashRoomPlayer = new HashMap<Room, ArrayList<Player>>();

        List<Room> roomList = new ArrayList<>();

        roomList = MethodsWeapons.roomsThatIsee(player);
        roomList.remove(player.getSquare().getRoom()); //controllo che non sia la mia
        for (Room roomIterate : roomList) {
            hashRoomPlayer.put(roomIterate, (ArrayList) roomIterate.getPlayerRoomList());
        }

        return hashRoomPlayer;

    }


    public void basicMode (Room room)
    {
        for (Player playerIterate : room.getPlayerRoomList())
        {
            doDamage(playerIterate, 1);
        }
        isLoaded = false;

    }



    //stesso discorso: square o players?

    public HashMap<Square, ArrayList<Player>> checkInCozyFireMode ()
    {
        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();

        List<Square> squareList = new ArrayList<>();
        squareList = (List) player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList)
        {
            hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());
        }

        return hashSquarePlayer;

    }


    public void inCozyFireMode (Square square)
    {
        List<Square> squareList = new ArrayList<>();
        squareList = (List) player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        squareList.remove(player.getSquare());

       for (Square squareIterate : squareList)
       {
           for (Player playerIterate : squareIterate.getPlayerList())
           {
               doDamage(playerIterate, 1);
               markTarget(playerIterate, 1);
           }

           isLoaded = false;
       }

    }



}