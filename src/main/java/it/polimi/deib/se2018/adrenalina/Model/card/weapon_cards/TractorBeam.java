package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons.playersReachable;


public class TractorBeam extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];

    public TractorBeam( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Raggio Traente";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {

        if(((ResponseTractatorBeam) responseMessage).isMode())

            punisherMode(((ResponseTractatorBeam) responseMessage).getTargetAlternativeMode());
        else
        basicMode(((ResponseTractatorBeam) responseMessage).getTargetBasicMode(), ((ResponseTractatorBeam) responseMessage).getX() ,((ResponseTractatorBeam) responseMessage).getY());
    }

    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])
            return new RequestTractatorBeam(checkAvailableMode(),checkMoveBasicMode(),checkPunisherMode());

        else
            return new RequestTractatorBeam(checkAvailableMode(),checkMoveBasicMode(),new LinkedList<>());

    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        availableMethod[1] = false;
        availableMethod[0] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.getAmmoYellow()>0&& player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[1] = true;



        return availableMethod;

    }
    /**
     * Calculate in which square a player can be moved using the basic mode
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Map<ColorId,List<String>> checkMoveBasicMode() throws IllegalStateException
    {

        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
        Map<ColorId,List<String>> result = new HashMap<>();

        List<Room> roomReachablePlayer = MethodsWeapons.roomsThatIsee(this.player);
        for(Player t: playersReachable( player.getSquare(),3))
        {
         for (Room reachRoom : roomReachablePlayer)
            {

                for(Square sq : t.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(t.getColor()))
                                        .collect(Collectors.toList()).get(0).getSquare().getX(),player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(t.getColor()))
                                            .collect(Collectors.toList()).get(0).getSquare().getY(),2) ){

                    if (reachRoom.getSquareList().contains(sq)) {

                        String coordinates = "x = "+sq.getX()+",y = "+ sq.getY();//Save the coordinates

                        result.putIfAbsent(t.getColor(), Collections.singletonList(coordinates)); //Add the square with the player at hash map




                    }
                 }
            }

        }

        return result;
    }
    /**
     * Return the list of all target available for using the punisher mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<ColorId> checkPunisherMode () throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
        List<ColorId> listPlayer = new LinkedList<>();


        for(Player p : player.getSquare().getGameBoard().getAllPlayer())
        {
            for(Square sq : p.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(),player.getSquare().getY(),2) )
            {



                if (this.player.getSquare().equals(sq)) {
                    listPlayer.add(p.getColor());
                }

            }
        }

        return listPlayer;//Returns squares
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param colorPlayer player affected by weapon
     */
    public void basicMode(ColorId colorPlayer, int x, int y) throws IllegalStateException
    {

        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);
        moveTarget(player,x,y);//Move the target
        this.isLoaded = false;
    }

    /**
     * It uses the punisher mode of the lock rifle
     * @param colorPlayer player affected by weapon
     */
    public void punisherMode(ColorId colorPlayer) throws  IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),3);

        moveTarget(player,player.getSquare().getX(),player.getSquare().getY());//Move the target
        this.isLoaded = false;
        this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
    }
}