package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestTractatorBeam;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseTractatorBeam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * It represents the card TractatorBeam
 * it uses two method for the two alternative modality checkMoveBasicMode() and checkPunisherMode ()
 *
 * @author Karroot
 */
public class TractorBeam extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];

    /**
     * Create the card TractatotBeam
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public TractorBeam( Color color, int weaponID, boolean isLoaded)  throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "Raggio Traente";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }

    /**
     * this method is used to use the weapon
     * @param responseMessage response message specified for the weapon
     */
    @Override
    public void useWeapon(ResponseInput responseMessage) {

        if(((ResponseTractatorBeam) responseMessage).isMode())

            punisherMode(((ResponseTractatorBeam) responseMessage).getTargetAlternativeMode());
        else
            basicMode(((ResponseTractatorBeam) responseMessage).getTargetBasicMode(), ((ResponseTractatorBeam) responseMessage).getX() ,((ResponseTractatorBeam) responseMessage).getY());
    }
    /**
     * method used to return the reqeuest message
     * @return the asked request input
     */
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
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        availableMethod[1] = false;
        availableMethod[0] = false;


        if (isLoaded() && !this.checkMoveBasicMode().isEmpty())
            availableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.getAmmoYellow()>0&& MethodsWeapons.playersReachable(this.player.getSquare(),2).size()>1) {
            availableMethod[1] = true;

        }


        return availableMethod;

    }
    /**
     * Calculate in which square a player can be moved using the basic mode and give you which enemy can be attacked
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Map<ColorId,List<String>> checkMoveBasicMode() throws IllegalStateException
    {

        if (!this.isLoaded())
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");
        if (this.player==null)
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");
        Map<ColorId,List<String>> result = new HashMap<>();

        List<Room> roomReachablePlayer = MethodsWeapons.roomsThatIsee(this.player);
        for(Player t: player.getSquare().getGameBoard().getAllPlayer())
        {
            if(!t.equals(this.player))
            {
                for (Room reachRoom : roomReachablePlayer)
                {

                    for(Square sq : t.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(t.getColor()))
                                            .collect(Collectors.toList()).get(0).getSquare().getX(),player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(t.getColor()))
                                                .collect(Collectors.toList()).get(0).getSquare().getY(),2) ){ //give us all the square at distance 2 that t can go

                        if (reachRoom.getSquareList().contains(sq)) {

                            String coordinates = sq.toStringCoordinates();//Save the coordinates
                            if(result.get(t.getColor())!=null)
                            {
                                List<String> tempList =result.get(t.getColor());
                                        tempList.add(coordinates);
                            }
                            else {
                                List<String> coordinatesList = new LinkedList<>();
                                coordinatesList.add(coordinates);
                                result.put(t.getColor(), coordinatesList); //Add the square with the player at hash map
                                result.isEmpty(); // da togliere
                            }



                        }
                     }
                }

            }
        }

        return result;
    }
    /**
     * Return the list of all target available for using the punisher mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the punisher mode can't be used
     */
    public List<ColorId> checkPunisherMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità punisher dell'arma "+name+" non eseguibile.");
        List<ColorId> listPlayer = new LinkedList<>();


        for(Player p : player.getSquare().getGameBoard().getAllPlayer())
        {
            if(!p.equals(this.player))
            {
                for(Square sq : p.getSquare().getGameBoard().getArena().squareReachableNoWall(p.getSquare().getX(),p.getSquare().getY(),2) )
                {



                    if (this.player.getSquare().equals(sq)) {
                        listPlayer.add(p.getColor());
                    }

                }
            }
        }

        return listPlayer;//Returns squares
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param colorPlayer player affected by weapon
     * @param x coordinates x where player want to move enemy colorplayer
     * @param y coordinates y where player want to move enemy colorplayer
     * @throws  IllegalStateException if the aviable mode [0] is non true
     */
    public void basicMode(ColorId colorPlayer, int x, int y) throws IllegalStateException
    {

        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");


        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
        {

            doDamage(player.getSquare().getGameBoard().getTermi(),1);

        }
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
        {

            moveTarget(player.getSquare().getGameBoard().getTermi(),x,y);

        }
        else  {
            for(Player p: player.getSquare().getGameBoard().getAllPlayer())
            {
            if(p.getColor().equals(colorPlayer))
                moveTarget(p,x,y);//Move the target
            }
        }
        isLoaded = false;
    }

    /**
     * It uses the punisher mode of the lock rifle
     * @param colorPlayer player affected by weapon
     * @throws  IllegalStateException if the aviable mode [1] is non true
     */
    public void punisherMode(ColorId colorPlayer) throws  IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");


        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
        {

                doDamage(player.getSquare().getGameBoard().getTermi(),3);



        }
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),3);

        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
        {

                moveTarget(player.getSquare().getGameBoard().getTermi(),player.getSquare().getX(),player.getSquare().getY());

        }
        else{
            for(Player p: player.getSquare().getGameBoard().getAllPlayer()){
                if(p.getColor().equals(colorPlayer))
                    moveTarget(p,player.getSquare().getX(),player.getSquare().getY());//Move the target
            }
        }
        isLoaded = false;
        this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
    }
}